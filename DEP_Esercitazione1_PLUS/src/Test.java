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
		RandomAccessFile file = new RandomAccessFile("esempio.txt", "r");
		Token token;
		LexicalAnalayzer lex= new LexicalAnalayzer(file);
		PrintWriter file2 = new PrintWriter("output.txt");
		while(!(token=lex.nextToken()).getName().equals("EOF")) {
		
			System.out.println(token);
			file2.println(token);
		}
		
		file.close();
		file2.close();

	}

}
