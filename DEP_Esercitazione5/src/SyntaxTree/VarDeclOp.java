/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */
package SyntaxTree;

import Visitor.Visitor;

public class VarDeclOp extends UseNode {

	public VarDeclOp(String op, Leaf type, OpNode varInitOp) {
		super(op,type,varInitOp);

	}

	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}

}
