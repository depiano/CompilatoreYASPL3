/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * wEb site: http://www.depiano.it
 * 
 *
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Test {

	public static void main(String[] args) throws Exception {
		//File d'input aperto in lettura
		RandomAccessFile file = new RandomAccessFile("esempio.txt", "r");
		Token token;
		LexicalAnalayzer lex= new LexicalAnalayzer(file);
		//File di output aperto in scrittura
		PrintWriter file2 = new PrintWriter("output.txt");
		//Chiedo a lex i token, ricavo il token-name e lo confronto con EOF
		while(!(token=lex.nextToken()).getName().equals("EOF")) {
		
			//stampo il token-name
			System.out.println(token);
			//Stampo il token-name nel file
			file2.println(token);
		}
		//Chiudo i due file
		file.close();
		file2.close();

	}

}
