package SyntaxTree;

import Visitor.Visitor;

public class MathOp extends OpNode {

	public MathOp(String op, Node expr1, Node expr2) {
		super(op,expr1 ,expr2);
		
	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	
	public Node getFirstExpr(){
		return this.nodeList().get(0);
	}
	
	public Node getSecondExpr(){
		return this.nodeList().get(1);
	}

}
