package SyntaxTree;

import Visitor.Visitor;

public class CaseListOp extends OpNode {

	public CaseListOp(String op, Node casedefaultop) {
		super(op, casedefaultop);
	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
}
