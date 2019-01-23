package SyntaxTree;

import Visitor.Visitor;

public class IfThenElseOp extends ConditionalOp {

	public IfThenElseOp(String op, Node expr, Node compStat1, Node compStat2) {
		super(op,expr,compStat1,compStat2);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
