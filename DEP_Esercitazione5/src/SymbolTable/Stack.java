/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package SymbolTable;

public interface Stack<T> {

	void push(T t);
	T pop();
	T top();
	boolean isEmpty();
	
}
