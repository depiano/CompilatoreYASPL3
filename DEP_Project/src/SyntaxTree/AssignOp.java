package SyntaxTree;
import Visitor.Visitor;

public class AssignOp extends UseNode {

	public AssignOp(String op, Leaf id, Node expr) {
		super(op,id, expr);
	
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
