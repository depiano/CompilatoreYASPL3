/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java_cup.runtime.Symbol;



public class Test {

	public static void main(String[] args) throws IOException {

		//File d'input
		String FileName="input.txt";
		File source = new File(FileName);
		FileInputStream file= null;
		file=new FileInputStream(source);
		LexerLex lexer = new LexerLex(file);
		Symbol toPrint;
		//Ciclo sui token e confronto il codice sym del token con EOF
		while( ((toPrint = lexer.next_token()).sym) != LexerSym.EOF) {

			//Se è un ID
			if(toPrint.sym==LexerSym.ID) {
				//Dato l'indice cerco il valore ad esso associato
				toPrint = SymbolTable.findKey((int) toPrint.value);
			}
			//stampo i token <token-name,attribute-value>
			System.out.println("<"+toPrint.sym+", '"+toPrint.value+"'>");

		}
		file.close();

	}

}
