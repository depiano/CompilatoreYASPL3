package SyntaxTree;

import Visitor.Visitor;

public class ArrayOp extends OpNode {

	public ArrayOp(String op, Node type, Node arrayindex) {
		super(op, type, arrayindex);
		// TODO Auto-generated constructor stub
	}

	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
}
