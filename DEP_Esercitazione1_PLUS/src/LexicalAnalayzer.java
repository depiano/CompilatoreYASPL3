/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * wEb site: http://www.depiano.it
 * 
 *
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;



public class LexicalAnalayzer {
	private static int contatore;
	private int state;
	private RandomAccessFile  file;
	private Token token;
	private Character character;
	private int index;
	private String lessema;
	private HashMap<Integer, String> tabSimboli;


	public LexicalAnalayzer(RandomAccessFile f) throws IOException{
		this.file = f;
		this.state=0;
		this.index=0;
		this.lessema="";
		this.token= null;
		this.tabSimboli=new HashMap<Integer, String>();
		insertKeywordIntoTable();
	}

	public Token nextToken() throws Exception {


		while(true) {

			//System.out.println("state= "+state+" "+"c= "+character+" "+"lessema= "+lessema);
			switch (state) {
			case -1:{
				state=50;
				token= new Token("EOF");
			}
			break;
			case 0:{
				index= file.read();

				if(index!=-1) {

					character=(char) index;

					if(this.character=='<') {
						this.state=1;}
					else if(this.character=='=') {
						this.state=5;}
					else if(this.character=='>') {
						this.state=6;}
					else {
						this.state=9;
					}
				}
				else {
					state=-1;

				}

			}
			break;
			case 1:{
				index= file.read();

				if(index!=-1) {


					character=(char) index;

					if(this.character=='=') {
						this.state=2;
					}
					else if(this.character=='>') {
						this.state=3;

					}
					else if(this.character=='<') {
						this.state=42;

					}
					else if(this.character=='-') {
						this.state=40;

					}
					else {
						state=4;
					}	}
				else {
					state=50;
					token=new Token("relop","LT");
				}
			}
			break;
			case 2:{
				state=50;
				token=new Token("relop","LE");
			}
			break;
			case 3:{
				state=50;

				token=new Token("relop","NE");
			}
			break;
			case 4:{
				state=50;
				retract(this.file);
				token=new Token("relop","LT");
			}
			break;
			case 5:{
				state=50;

				token=new Token("relop","EQ");

			}
			break;
			case 6:{
				index= file.read();

				if(index!=-1) {


					character=(char) index;
					if(character=='=') {
						this.state=7;
					}
					else if(this.character=='>'){
						this.state=42;
					}

					else {
						this.state=8;
					}
				}else {
					state=50;//new
					token= new Token("relop","GT");
				}
			}
			break;
			case 7:{
				state=50;

				token=new Token("relop","GE");
			}

			break;
			case 8:{
				state=50;
				retract(file);
				token=new Token("relop","GT");
			}
			break;

			case 9:{

				if(index!=-1) {
					lessema="";

					if(Character.isAlphabetic(this.character)) {
						lessema+=this.character;
						state=10;

					}
					else {
						state=12;
					}
				}else {
					state=50;
					token=insetIntoTable(this.lessema);
				}
			}
			break;
			case 10:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(Character.isLetterOrDigit(this.character)) {

						lessema+=this.character;
						state=10;

					}
					else {
						state=11;

					}
				}else {
					state=50;
					token=insetIntoTable(this.lessema);
				}
			}
			break;
			case 11:{

				state=50;
				retract(file);
				token=insetIntoTable(this.lessema);

			}
			break;
			case 12:{
				lessema="";
				if(index!=-1) {

					if(Character.isDigit(this.character)) {
						state=13;
						lessema+=character;
					}
					else {
						state=22;


					}
				}
				else {
					state=50;
					token= new Token("NUMBER", lessema);
				}
			}

			break;

			case 13:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(Character.isDigit(this.character)) {
						state=13;
						lessema+=character;
					}
					else if(this.character=='.') {
						state=14;
						lessema+=character;
					}
					else if(this.character=='E') {
						state=16;
						lessema+=character;
					}
					else {
						state=20;
					}

				}else {
					state=50;
					token= new Token("NUMBER", lessema);
				}
			}
			break;

			case 14:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(Character.isDigit(this.character)) {
						state=15;
						lessema+=character;
					}

					else {
						state=50;
						retract(file);
						retract(file);
						token= new Token("NUMBER",lessema=lessema.substring(0,lessema.length()-1));

					}
				}
				else {
					state=50;
					retract(file);
					token= new Token("NUMBER",lessema=lessema.substring(0,lessema.length()-1));
				}
			}
			break;
			case 15:{	
				index= file.read();

				if(index!=-1) {
					character=(char) index;
					if(Character.isDigit(this.character)) {
						state=15;
						lessema+=character;
					}
					else if(this.character=='E') {
						state=16;
						lessema+=character;
					}
					else {
						state=21;

					}
				}
				else {
					state=50;
					token= new Token("NUMBER", lessema);
				}
			}
			break;

			case 16:{
				index= file.read();

				if(index!=-1) {
					character=(char) index;
					if(this.character=='+'||this.character=='-') {
						state=17;
						lessema+=character;
					}
					else if(Character.isDigit(this.character)) {
						state=18;
						lessema+=character;
					}
					else {

						state=50;
						retract(file);
						token=new Token("ERROR");//

					}

				}
				else {
					state=50;

					token=new Token("ERROR");

				}
			}
			break;
			case 17:{
				index= file.read();

				if(index!=-1) {
					character=(char) index;
					if(Character.isDigit(this.character)) {
						state=18;
						lessema+=character;
					}
					else {

						state=50;
						retract(file);
						retract(file);
						token= new Token("ERROR");

					}
				}else {
					state=50;

					retract(file);
					token= new Token("ERROR");
				}
			}
			break;
			case 18:{
				index= file.read();

				if(index!=-1) {
					character=(char) index;
					if(Character.isDigit(this.character)) {
						state=18;
						lessema+=character;
					}

					else {
						state=19;

					}

				}
				else {
					state=50;
					token= new Token("NUMBER", lessema);
				}
			}
			break;
			case 19:
			case 20:
			case 21:{
				state=50;
				retract(file);
				token= new Token("NUMBER", lessema);

			}
			break;

			case 22:
				if(index!=-1) {

					if(this.character==' ') {
						state=23;
					}
					else if(this.character=='\n') {
						state=23;
					}
					else if(this.character=='\r') {
						state=23;
					}
					else if(this.character=='\t') {
						state=23;
					}
					else {

						state=25;

					}
				}
				else {
					state=80;
				}
				break;

			case 23:{
				index= file.read();

				if(index!=-1) {
					character=(char) index;
					if(this.character==' ') {
						state=23;
					}
					else if(this.character=='\n') {
						state=23;
					}
					else if(this.character=='\r') {
						state=23;
					}else if(this.character=='\t') {
						state=23;
					}
					else {
						state=24;
					}
				}else {
					state=0;
				}
			} 
			break;
			case 24:{
				state=0;
				retract(file);
			}
			break;



			case 25:{
				if(this.character=='/') {
					state=26;
					lessema+=this.character;
				}
				else {
					state=43;

				}

			}
			break;
			case 26:{
				index= file.read();

				if(index!=-1) {
					character=(char) index;
					if(this.character=='*') {
						state=27;
						lessema+=this.character;
					}
					else {
						retract(file);

						state=30;
					}
				}
				else{
					state=30;
				}

			}

			break;

			case 27:{

				index= file.read();

				if(index!=-1) {
					character=(char) index;


					if(this.character=='*') {
						state=28;
						lessema+=this.character;
					}

					else if(Character.isLetterOrDigit(this.character)){
						lessema+=this.character;
						state=27;


					}
					else if(!Character.isLetterOrDigit(this.character)){
						lessema+=this.character;
						state=27;
					}



					else {
						state=30;
					}
				}
				else {
					state=50;
					token=new Token("ERROR");
				}


			}
			break;
			case 28:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(this.character=='/') {
						state=29;
						lessema+=this.character;
					}
					else if(Character.isLetterOrDigit(this.character)){
						lessema+=this.character;
						state=28;
					}

					else if(!Character.isLetterOrDigit(this.character)){
						lessema+=this.character;
						state=28;
					}

					else {
						state=30;
					}
				}
				else {
					state=50;
					token= new Token("ERROR");
				}

			}
			break;
			case 29:{
				state= 50;

				token= new Token("COMMENT",lessema);
			}
			break;

			case 30:{
				if(this.character=='/') {
					state=31;
					lessema=""+this.character;
				}
				else {
					state=80;
				}
			}
			break;
			case 31:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(this.character=='/') {
						state=32;
						lessema+=this.character;
					}
					else {
						state=80;
					}
				}else {
					state=80;
				}
			}
			break;

			case 32:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(Character.isLetterOrDigit(this.character)) {
						state=32;
						lessema+=this.character;
					}
					else if(this.character=='\n') {
						state=50;
						token=new Token("LINE_COMMENT",lessema);

					}
					else if(!Character.isLetterOrDigit(this.character)) {
						state=32;
						lessema+=this.character;

					}
					else {
						state=80;}
				}
				else {
					state=50;
					token=new Token("Error");

				}
			}
			break;



			case 40:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(this.character=='-') {
						state=41;
					}
					else {
						state=50;
						retract(file);
						retract(file);
						token= new Token("RELOP","LT");

					}
				}
				else{

					state=50;
					retract(file);
					token= new Token("RELOP","LT");
				}
			}
			break;
			case 41:{
				state=50;

				token= new Token("ASSIGN");
			}

			break;

			case 42:{
				state=50;
				token= new Token("ASSIGN");
			}

			break;


			case 43:{
				if(this.character=='-') {
					state=44;
				}
				else state=80;

			}
			break;
			case 44:{

				index= file.read();
				if(index!=-1) {
					character=(char) index;

					if(this.character=='-') {
						state=45;
					}
					else { 
						state=50;
						token= new Token("MINUS");
						retract(file);



					}
				}
				else {

					state=80;
				}
			}
			break;
			case 45:{
				index= file.read();
				if(index!=-1) {
					character=(char) index;
					if(this.character=='>') {
						state=50;
						token=new Token("ASSIGN");
					}
					else { 
						state=50;
						token=new Token("MINUS");
						retract(file);
						retract(file);

					}
				}
				else {
					state=80;
					retract(file);

				}
			}
			break;


			case 80:{ 	


				if(character==';') {
					state= 50;
					token= new Token("PUNCT");
				}
				else if(character=='+') {
					state= 50;
					token= new Token("PLUS");
				}

				else if(character=='*') {
					state= 50;
					token= new Token("MUL");
				}
				else if(character=='-') {
					state= 50;
					token= new Token("MINUS");
				}
				else if(character=='(') {
					state= 50;
					token= new Token("LRPAR");
				}
				else if(character==')') {
					state= 50;
					token= new Token("RRPAR");
				}
				else if(character=='[') {
					state= 50;
					token= new Token("LSPAR");
				}
				else if(character==']') {
					state= 50;
					token= new Token("RSPAR");
				}
				else if(character==',') {
					state= 50;
					token= new Token("COMMA");
				}
				else{

					state=50;

					token= new Token("ERROR");
				}

			}
			break;





			case 50:{
				state=0;
				return token;
			}

			default:
				break;
			}
		}
	}


	public Token insetIntoTable(String lessema) {
		Token tk;
		lessema=lessema.toUpperCase();
		if(this.tabSimboli.containsValue(lessema)) {
			if(iskeyword(lessema)) {
				tk= new Token(lessema);	
			}
			else {
				tk=new Token("ID", lessema);
			}
		}
		else {
			tabSimboli.put(contatore, lessema);
			tk=new Token("ID", lessema);
			contatore++;
		}
		return tk;
	}


	public boolean iskeyword(String word) {
		return(word.equals("IF") || word.equals("THEN") || word.equals("ELSE") || word.equals("ENDIF") 
				|| word.equals("NEW") || word.equals("FOR") || word.equals("WHILE") || word.equals("DO") 
				|| word.equals("RETURN") || word.equals("SWITCH"));
	}


	public void retract(RandomAccessFile file) {
		try {
			file.seek(file.getFilePointer()-1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertKeywordIntoTable() throws IOException{
		String s;
		File f= new File("keyword.txt");
		FileReader fileKey = new FileReader(f);
		BufferedReader br = new BufferedReader(fileKey);
		while(true) {
			s=br.readLine();
			if(s==null) {
				break;
			}
			tabSimboli.put(contatore,s);
			contatore++;
		}
		br.close();
	}


}


