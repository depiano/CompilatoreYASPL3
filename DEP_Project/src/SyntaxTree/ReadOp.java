package SyntaxTree;

import Visitor.Visitor;

public class ReadOp extends UseNode {

	public ReadOp(String op,OpNode vars) {
		super(op, vars);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
