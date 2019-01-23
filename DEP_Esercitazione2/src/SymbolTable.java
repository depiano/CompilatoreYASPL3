/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

import java.util.Hashtable;

import java_cup.runtime.Symbol;


public class SymbolTable {
	//Indice della tabella dei simboli
	private  static int count=0;
	//Mappa utilizzata per implementare la tabella dei simboli
	private static Hashtable<Integer, String> SymbolTable= new Hashtable<Integer, String> ();	


	//Ritorna l'indice del lessem inserito o già presente nella tabella dei simboli
	public static int addIntoTable(String lessema) {
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

	//Cerca un lessema per key, ossia il numero.
	public static Symbol findKey(int key) {
		Symbol symbol = null;
		if(SymbolTable.containsKey(key)) {
			symbol= new Symbol(LexerSym.ID,SymbolTable.get(key));
		}
		return symbol;
	}

	//Cerca un lessema nella tabella dei simboli. Ritorna l'indice
	public static int findValue(String lessema) {
		int index=-1;
		Symbol symbol = null;
		for(int i=0;i<SymbolTable.size();i++) {
			if(lessema.equals(SymbolTable.get(i))) {
				symbol= new Symbol(LexerSym.ID,SymbolTable.get(i));
				index=i;
				break;
			}
		}
		return index;
	}


	
	
}
