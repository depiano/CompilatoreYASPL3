package SyntaxTree;

import Visitor.Visitor;

public class CaseOp extends OpNode{
	
	public CaseOp(String op, Node expr, Node vardecls, Node statements) {
		super(op,expr, vardecls, statements);
	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	
}
