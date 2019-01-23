/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */
package SyntaxTree;

import java.util.ArrayList;

import SyntaxTree.Node;
import Visitor.Visitor;

public class OpNode extends Node {

	private ArrayList<Node> val = new ArrayList<Node>();

	public OpNode(String op, Node...list) {
		super(op);
		for (Node node : list) {
			val.add(node);
		}
	}
	
	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

	public void addNode(Node n){
		val.add(n);
	}

	public ArrayList<Node> nodeList(){
		return val;
	}
	public int getSize() {
		return val.size();
	}

}
