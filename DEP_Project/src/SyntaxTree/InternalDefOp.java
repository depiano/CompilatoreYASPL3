package SyntaxTree;

import Visitor.Visitor;

public class InternalDefOp extends OpNode{

	public InternalDefOp(String op, Node procdeclop) {
		super(op, procdeclop);
		// TODO Auto-generated constructor stub
	}

	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
}
