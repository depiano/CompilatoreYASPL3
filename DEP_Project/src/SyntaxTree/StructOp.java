package SyntaxTree;

import Visitor.Visitor;

public class StructOp extends ScopeNode{

	public StructOp(String op, Leaf id, Node vardecls) {
		super(op, id,vardecls);
	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
