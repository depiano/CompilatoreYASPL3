/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package visitor;

import syntaxTree.Leaf;
import syntaxTree.Node;
import syntaxTree.OpNode;

public interface Visitor {

	Object visit(Node node);
	Object visit(OpNode node);
	Object visit(Leaf node);
	
}
