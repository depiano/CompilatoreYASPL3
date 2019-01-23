/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ParserDiscesaRicorsiva {

	static int puntatore;
	
	static String tok;
	//ArrayList di tutti i token prodotti dall'analizzatore lessicale
	static ArrayList<Token> tokenList;

	public static void main(String[] args) {

		try {
			//File d'input
			RandomAccessFile filein = new RandomAccessFile("input2.txt", "r");
			//File d'output
			PrintWriter fileout = new PrintWriter("output.txt");
			Token token;
			LexicalAnalayzer lex= new LexicalAnalayzer(filein);
			String toReturn= "";
			tokenList= new ArrayList<Token>();

			/*
			 * Carico l'ArrayList con tutti i token prodotti dall'analizzatore lessicale
			 * e stampo il token-name in console
			*/
			while(!(token=lex.nextToken()).getName().equals("EOF")) {
				tokenList.add(token);
				System.out.println(token.name);
			}
			
			//se i token prodotti sono <2 non possiamo costruire l'albero di parsing
			if(tokenList.size() < 2) {
				System.out.println("La stringa in input non è valida!");
				return;
			}

			//Da qui inizia il parser a discesa ricorsiva
			puntatore = 0;
			try {
				boolean isValid = P();
				if( (isValid) && (puntatore == tokenList.size()) ) {
					toReturn = "La stringa in input è valida";
					System.out.println(toReturn);
				}else {
					toReturn = "La stringa in input non è valida!";
					System.out.println(toReturn);
				}
			}catch (Exception e) {
				e.printStackTrace();
				toReturn = "La stringa in input non è valida!";
				System.out.println(toReturn);
			}

			fileout.println(toReturn);

			filein.close();
			fileout.close();

		}catch (FileNotFoundException ex) {
			System.out.println("File non trovato");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * PRODUZIONE: Program -> Stmt Program1
	 * 
	 * Stmt -> IF Expr THEN Stmt
	 * Stmt -> ID ASSIGN Expr
	 * 
	 */
	
	static boolean P() {
		int backtrack = puntatore;
		// controlla Stmt
		if(S()==false) {
			//faccio backtrack
			puntatore=backtrack;
			return false;
		}
		//Controlla Program1 
		if(P1()==false) {
			//faccio backtrack
			puntatore=backtrack;
			return false;
		}
		
		return true;
	}

	/*
	 * PRODUZIONE: Program -> Stmt Program1
	 * 
	 * Program1 -> ; Stmt Program1
	 * Program1 -> (epsilon)
	 * 
	 */
	static boolean P1() {

		int backtrack = puntatore;
		//Controlla se il puntatore non ha raggiunto la fine dell'ArrayList
		if(prosegui()==true) {

			//Il token-name deve essere PUNCT
			if(!tokenList.get(puntatore++).name.equalsIgnoreCase("PUNCT")) {
				puntatore=backtrack;
				return true;
			}

		}	else {

			return true;
		}

		//Controlla che segue uno Stmt
		if(S()==false) {
			puntatore=backtrack;
			return false;
		}
		//Controlla che segue un Program1
		if(P1()==false) {
			puntatore=backtrack;
			return false;
		}

		return true;


	}
	
	/*
	 * Controlla se le produzioni sono:
	 * 
	 * Stmt -> IF Expr THEN Stmt
	 * Stmt -> ID ASSIGN Expr
	 */
	static boolean S() {
		int backtrack = puntatore;
		//se è un IF
		if(isIf()==true) {
			//Controllo se segue un Expr
			if(E()==false) {

				puntatore=backtrack;
				return false;
			}
			//Controllo se segue un THEN
			if(isThen()==false) {

				puntatore=backtrack;
				return false;
			}
			//Controllo se segue una Stmt
			if(S()==false) {
				puntatore=backtrack;
				return false;
			}
			return true;
		}else {
			//Controllo se segue un ID
			if(isId()==false) {
				puntatore=backtrack;
				return false;
			}
			
			/*
			 * Controllo se segue un ASSIGN
			 * l'ASSIGN nel nostro analizzatore lessicale è così rappresentato
			 *  <--
			 */
			if(isAssign()==false) {
				puntatore=backtrack;
				return false;
			}
			//Controllo se segue un Espressione
			if(E()==false) {
				puntatore=backtrack;
				return false;



			}
			/*
			 * Se sono tutti TRUE i controlli precedenti allora la produzione
			 *  è rispettata e lo statement è accettato dalla grammatica.
			 */
			return true;
		}

	}


	/*
	 * Controlla se le produzioni sono:
	 * 
	 * Expr -> Term Expr1
	 */
	static boolean E() {
		int backtrack = puntatore;
		//Controlla se la produzione è Term -> ID or Term -> NUMBER
		if(T()==false) {
			puntatore=backtrack;
			return false;
		}
		//Controlla se la produzione è Expr1 -> RELOP Term or Expr -> (epsilon)
		if(E1()==false) {
			puntatore=backtrack;
			return false;
		}
		return true;
	}


	/*
	 * Controlla se le produzioni sono:
	 * 
	 * Expr1 -> RELOP Term
	 * Expr1 -> (epsilon)
	 */
	static boolean E1() {
		int backtrack = puntatore;
		//Controlla il puntatore non ha raggiunto la fine dell'ArrayList
		if(prosegui()==true) {
			//Controlla che segue un RELOP
			if( isRelop() == true ) {
				//Controlla che segue un Term -> ID or Term -> NUMBER
				if( T() == false ) {
					puntatore = backtrack;
					return false;
				}

				return true;
			}
			else {
				return true;
			}
		}
		return true;
	}


	/*
	 * Controlla se le produzioni sono:
	 * 
	 * Term -> ID
	 * Term -> NUMBER
	 */
	static boolean T() {

		//Controlla che ci sia un token-name ID
		if( isId() == true ) {

			System.out.println("sono un ID");
			return true;
		}
		else {
			//Controlla che ci sia un token-name NUMERIC
			if( isNumeric() == true ) {
				System.out.println("sono un num");
				return true;
			}
		}
		return false;
	}

	/*
	 * Controlla se il token-name prodotto dall'analizzatore lessicale è IF
	 */
	static boolean isIf() {
		if(tokenList.get(puntatore).name.equals("IF")) {
			System.out.println("sono un if");
			puntatore++;
			return true;
		}
		else
			return false;


	}

	//Controlla se il token-name prodotto dall'analizzatore lessicale è THEN
	static boolean isThen() {

		if(tokenList.get(puntatore).name.equalsIgnoreCase("THEN")) {
			System.out.println("sono un then");
			puntatore++;
			return true;
		}
		else {
			return false;
		}


	}

	/*
	 * Controlla se il token-name prodotto dall'analizzatore lessicale è ID
	 */
	static boolean isId() {

		if(tokenList.get(puntatore).name.equalsIgnoreCase("ID")) {
			System.out.println("sono un ID");
			puntatore++;
			return true;
		}
		else {
			return false;
		}



	}

	/*
	 * Controlla se il token-name prodotto dall'analizzatore lessicale è ASSIGN
	 */
	static boolean isAssign() {

		if(tokenList.get(puntatore).name.equalsIgnoreCase("ASSIGN")) {
			System.out.println("Sono un =");
			puntatore++;
			return true;
		}
		else {
			return false;
		}

	}

	/*
	 * Controlla che non sono stati esauriti tutti i token prodotti
	 * dall'analizzatore lessicale.
	 */
	static boolean prosegui() {

		if(puntatore<tokenList.size()) {
			return true;
		}
		return false;
	}

	/*
	 * Controlla se il token-name prodotto dall'analizzatore lessicale è RELOP
	 */
	static boolean isRelop() {


		if(tokenList.get(puntatore).name.equalsIgnoreCase("RELOP")) {
			System.out.println("sono un relop");
			puntatore++;
			return true;
		}
		else {
			System.out.println("non sono un relop");
			return false;
		}

	}
	/*
	 * Controlla se il token-name prodotto dall'analizzatore lessicale è NUMBER
	 */
	static boolean isNumeric() {

		if(tokenList.get(puntatore++).name.equalsIgnoreCase("NUMBER")) {


			return true;
		}
		else {
			return true;
		}



	}

}



