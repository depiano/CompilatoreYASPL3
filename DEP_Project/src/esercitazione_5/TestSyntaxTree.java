/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package esercitazione_5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import SyntaxTree.OpNode;
import Visitor.XMLVisitor;


public class TestSyntaxTree {
	
public static void main(String[] args) {
		
		//File d'input
		File file = new File("input2.txt");
		try {
			YASPL3Lex lexer = new YASPL3Lex(new FileInputStream(file));
			YASPL3Cup parser = new YASPL3Cup(lexer);
		    OpNode root = (OpNode) parser.parse().value;
			String xmlSource = root.accept(new XMLVisitor()).toString();
			System.out.println(xmlSource);
			//File d'output
			FileWriter fw = new FileWriter("output.xml");
		    fw.write(xmlSource);
		    fw.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
