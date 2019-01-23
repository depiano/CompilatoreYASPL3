package SyntaxTree;

import Visitor.Visitor;

public class CallOp extends UseNode {

	public CallOp(String op, Leaf id, OpNode args) {
		super(op, id, args);
		
	}
	public CallOp(String op, Leaf id) {
		super(op, id);
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}	

}
