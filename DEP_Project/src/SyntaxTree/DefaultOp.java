package SyntaxTree;

import Visitor.Visitor;

public class DefaultOp extends OpNode{
	
	public DefaultOp(String op, Node vardecls, Node statements) {
		super(op, vardecls, statements);
	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}


}
