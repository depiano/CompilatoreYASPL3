package esercitazione_5;
import java.io.File;
import java.io.FileInputStream;

import SymbolTable.SymbolTablesStack;
import SyntaxTree.ProgramOp;
import Visitor.SemanticVisitor;


public class TestSemantic {

	public static void main(String[] args) {

		File file = new File("input2.txt");
		try {
			YASPL3Lex  lexer = new	YASPL3Lex (new FileInputStream(file));
			YASPL3Cup parser = new YASPL3Cup(lexer);
			ProgramOp toPrint = (ProgramOp) parser.parse().value;
			
			SymbolTablesStack stack = new SymbolTablesStack();
			toPrint.accept(new SemanticVisitor(stack));
			System.out.println(stack.pop());
		    
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}

}
