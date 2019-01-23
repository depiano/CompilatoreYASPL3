package SyntaxTree;

import Visitor.Visitor;

public class WhileOp extends ConditionalOp {

	public WhileOp(String op, Node expr, Node compStat) {
		super(op, expr, compStat);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
