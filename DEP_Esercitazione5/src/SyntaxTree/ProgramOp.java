/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */
package SyntaxTree;

import Visitor.Visitor;

public class ProgramOp extends ScopeNode {

	public ProgramOp(String op, OpNode decls, OpNode statements) {
		super(op, decls,statements);
		
	}
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
}
