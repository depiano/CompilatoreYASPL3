package SyntaxTree;

import Visitor.Visitor;

public class ArrayAssignOp extends UseNode {

	public ArrayAssignOp(String op, Node arrayindex, Node expr) {
		super(op,arrayindex,expr);
	}

	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	
	public Node getFirstExpr(){
		return this.nodeList().get(0);
	}
	
	public Node getSecondExpr(){
		return this.nodeList().get(2);
	}
}
