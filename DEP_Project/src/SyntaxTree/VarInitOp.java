package SyntaxTree;

import Visitor.Visitor;

public class VarInitOp extends UseNode{

	public VarInitOp(String op, Node...expr) {
		
		super(op, expr);

	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
