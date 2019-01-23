/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package SymbolTable;

import java.util.Hashtable;

public class SymbolTable extends Hashtable<String, EntryInfo>{
	
private String scopeName;
	
	public SymbolTable(String ScopeName){
		super();
		this.scopeName = ScopeName;
	}
	
	public String getName(){
		return scopeName;
	}
	
	public void setName(String name){
		this.scopeName = name;
	}
	
	public String toString() {
		return "["+scopeName+"]: "+super.toString();
	}
	
}
