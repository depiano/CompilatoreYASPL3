package SyntaxTree;

import Visitor.Visitor;

public class SingleOp extends OpNode{

	public SingleOp(String op,Node expr) {
		super(op,expr);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
}
