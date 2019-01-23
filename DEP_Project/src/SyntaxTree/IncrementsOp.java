package SyntaxTree;

import Visitor.Visitor;

public class IncrementsOp extends OpNode {

	public IncrementsOp(String op, Leaf id) {
		super(op, id);
		// TODO Auto-generated constructor stub
	}

	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	
	

}
