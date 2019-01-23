/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package syntaxTree;

import syntaxTree.Node;
import visitor.Visitor;


public class Leaf extends Node{

	private String val;
	
	public Leaf(String op, String val){
		super(op);
		this.val = val;
	}
	
	public Object accept(Visitor v){
		return v.visit(this);
	}

	public Object getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
}