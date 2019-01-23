/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package esercitazione_4;
import java.util.Hashtable;

import java_cup.runtime.Symbol;

public class SymbolTable {
	
		private  static int count=0;
		private static Hashtable<Integer, String> SymbolTable= new Hashtable<Integer, String> ();	


		public static int addIdentifiers(String lessema) {
			int index=0;
			if(!SymbolTable.contains(lessema)) {
				SymbolTable.put(count, lessema);
				index=count;
				count++;
			}
			else {
				index=findValue(lessema);
			}
			return index;
		}


		public static Symbol lookup(int key) {
			Symbol symbol = null;
			if(SymbolTable.containsKey(key)) {
				symbol= new Symbol(YASPL3Sym.ID,SymbolTable.get(key));
			}
			return symbol;
		}

		public static int findValue(String lessema) {
			int index=-1;
			Symbol symbol = null;
			for(int i=0;i<SymbolTable.size();i++) {
				if(lessema.equals(SymbolTable.get(i))) {
					symbol= new Symbol(YASPL3Sym.ID,SymbolTable.get(i));
					index=i;
					break;
				}
			}
			return index;
		}


		
		
	}