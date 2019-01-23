package SyntaxTree;

import Visitor.Visitor;

public class SwitchOp extends OpNode{

	public SwitchOp(String op, Leaf id, Node caselist) {
		super(op, id, caselist);	}
	

	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
