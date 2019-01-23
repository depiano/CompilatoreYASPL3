/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */
package SyntaxTree;

import Visitor.Visitor;

public class ParOp  extends UseNode{

	public ParOp(String op, Leaf parType, Leaf type, Leaf id) {
		super(op, parType, type, id);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	
	
}
