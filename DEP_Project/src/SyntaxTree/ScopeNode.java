package SyntaxTree;

import SymbolTable.SymbolTable;
import Visitor.Visitor;

public class ScopeNode  extends UseNode{

	private SymbolTable symbolTable;
	
//	Scope ProgramOp
	public ScopeNode(String op, OpNode n1, OpNode n2) {
		super(op, n1, n2);
		symbolTable = null;
	}
	
	
	//Scope definizione funzione senza parametri
	public ScopeNode(String op, Leaf l, Node n) {
		super(op, l, n);
		symbolTable = null;
	}
	
	//Scope definizione funzione con parametri
	public ScopeNode(String op, Leaf l, OpNode n1, Node n2) {
		super(op, l, n1, n2);
		symbolTable = null;
	}
	
	//Scope definizione for
	public ScopeNode(String op, AssignOp assign, Node expr, AssignOp increments, Node corpo) {
		super(op, assign, expr, increments,corpo);
		symbolTable=null;
	}

	
	public Object accept(Visitor v) throws Exception{
		return v.visit(this);
	}
	
	public SymbolTable getSymbolTable(){
		return this.symbolTable;
	}
	
	public void setSymbolTable(SymbolTable symbolTable){
		this.symbolTable = symbolTable;
	}



}
