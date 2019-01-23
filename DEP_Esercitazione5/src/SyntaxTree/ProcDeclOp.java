/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */
package SyntaxTree;

import Visitor.Visitor;

public class ProcDeclOp extends ScopeNode {
//funzione con parametri
	public ProcDeclOp(String op, Leaf id ,OpNode parDecls, Node body) {
		super(op, id,parDecls,body);
		
	}
	//funzione senza parametri
	public ProcDeclOp(String op, Leaf id ,Node body) {
		super(op, id, body);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}



}
