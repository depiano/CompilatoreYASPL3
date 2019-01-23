/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package syntaxTree;

import java.util.ArrayList;

import syntaxTree.Node;
import visitor.Visitor;

public class OpNode extends Node {

	private ArrayList<Node> val = new ArrayList<Node>();

	public OpNode(String op, Node...list) {
		super(op);
		for (Node node : list) {
			val.add(node);
		}
	}
	
	public Object accept(Visitor v){
		return v.visit(this);
	}

	public void addNode(Node n){
		val.add(n);
	}

	public ArrayList<Node> nodeList(){
		return val;
	}

}
