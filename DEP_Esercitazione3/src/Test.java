/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Test {

	public static void main(String[] args) throws Exception {
		//File d'input
		RandomAccessFile file = new RandomAccessFile("input2.txt", "r");
		Token token;
		LexicalAnalayzer lex= new LexicalAnalayzer(file);
		//File d'output
		PrintWriter file2 = new PrintWriter("output.txt");
		//while su tutti i token. Il ciclo termina quando si incontra il token EOF
		while(!(token=lex.nextToken()).getName().equals("EOF")) {
			
			//Stampa nella console il token-name
			System.out.println(token);
			//Stampa il token-name nel file
			file2.println(token);
		}
	
		file.close();
		file2.close();

	}

}
