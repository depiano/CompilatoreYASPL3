package SyntaxTree;

import Visitor.Visitor;

public class IfThenOp  extends ConditionalOp{

	public IfThenOp(String op, Node expr, Node compStat) {
		super(op, expr,compStat);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
}
