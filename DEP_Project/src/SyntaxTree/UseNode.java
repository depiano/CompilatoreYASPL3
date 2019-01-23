package SyntaxTree;

import Visitor.Visitor;

public class UseNode extends OpNode {

	public UseNode(String op, Node...list) {
		super(op, list);
		
	}
	
	public UseNode(String op, Leaf l, Node n) {
		super(op, l, n);
	}
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

	
	public String getLeafType() {
		return ((Leaf) this.nodeList().get(0)).getType();
	}
	
	public Leaf getLeaf(){
		return ((Leaf) this.nodeList().get(0));
	}
	public String getName(){
		return this.getLeaf().getVal().toString();
	}
	
	public Node getNode(){
		if(this.nodeList().size()>1) {
	
			return this.nodeList().get(1);
		}else {
			return null;
		}
		
		
	}
	
	
}