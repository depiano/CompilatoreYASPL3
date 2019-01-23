package SyntaxTree;

import Visitor.Visitor;

public class ForOp extends ScopeNode{
	
	
	public ForOp(String op, AssignOp assign, Node expr, AssignOp increments, Node corpo) {
		super(op,assign,expr, increments, corpo);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
}
