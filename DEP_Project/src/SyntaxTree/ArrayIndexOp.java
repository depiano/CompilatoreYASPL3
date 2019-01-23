package SyntaxTree;

import Visitor.Visitor;

public class ArrayIndexOp extends OpNode{

	public ArrayIndexOp(String op, Leaf id, Node index) {
		super(op,id,index);
		// TODO Auto-generated constructor stub
	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	
	public Node getFirstExpr(){
		return this.nodeList().get(0);
	}
	
	public Node getSecondExpr(){
		return this.nodeList().get(1);
	}

}
