package SyntaxTree;

import Visitor.Visitor;

public class StructInitOp extends OpNode{

	public StructInitOp(String op, Node vardecls) {
		super(op, vardecls);
		// TODO Auto-generated constructor stub
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
