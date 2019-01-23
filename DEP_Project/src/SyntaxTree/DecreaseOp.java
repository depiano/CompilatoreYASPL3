package SyntaxTree;

import Visitor.Visitor;

public class DecreaseOp extends OpNode{
	
	public DecreaseOp(String op, Leaf id) {
		super(op, id);
	}

	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	

}
