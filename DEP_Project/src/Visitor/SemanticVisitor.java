package Visitor;

import java.util.ArrayList;


import FunctionUtility.FunctionTable;
import FunctionUtility.Paramater;
import SymbolTable.EntryInfo;
import SymbolTable.SymbolTable;
import SymbolTable.SymbolTablesStack;
import SyntaxTree.ArrayAssignOp;
import SyntaxTree.ArrayIndexOp;
import SyntaxTree.ArrayOp;
import SyntaxTree.AssignOp;
import SyntaxTree.CallOp;
import SyntaxTree.CaseListOp;
import SyntaxTree.CaseOp;
import SyntaxTree.ConditionalOp;
import SyntaxTree.DecreaseOp;
import SyntaxTree.DefaultOp;
import SyntaxTree.ForOp;
import SyntaxTree.UseNode;
import SyntaxTree.IfThenElseOp;
import SyntaxTree.IfThenOp;
import SyntaxTree.IncrementsOp;
import SyntaxTree.InternalDefOp;
import SyntaxTree.Leaf;
import SyntaxTree.MathOp;
import SyntaxTree.ModOp;
import SyntaxTree.Node;
import SyntaxTree.OpNode;
import SyntaxTree.ParOp;
import SyntaxTree.ProcDeclOp;
import SyntaxTree.ProgramOp;
import SyntaxTree.ReadOp;
import SyntaxTree.ScopeNode;
import SyntaxTree.SingleOp;
import SyntaxTree.StructInitOp;
import SyntaxTree.StructOp;
import SyntaxTree.SwitchOp;
import SyntaxTree.VarDeclOp;
import SyntaxTree.VarInitOp;
import SyntaxTree.WhileOp;
import SyntaxTree.WriteOp;


public class SemanticVisitor implements Visitor{





	private SymbolTablesStack stack;
	private String type= "no-type";
	//mi serve per prendere il nome della funzione
	private String functionName= null;
	private FunctionTable functionTable;
	private String switch_type=null;




	public SemanticVisitor(SymbolTablesStack stack) {

		this.stack= stack;
		this.functionTable= new FunctionTable("");
	}

	public Object visit(Node node) {

		return null;
	}

	public Object visit(OpNode node) throws Exception {

		//System.out.println("OPNODE");


		ArrayList<Node> nodes = node.nodeList();

		//intercetto la visita al Leaf in modo da settare i valoriper le varie inizializzazioni es:i=0;
		if (node.getOp().equals("InitializationOp")) {

			if(nodes.get(0).getOp().equals("INT_CONST")){
				node.setType("integer");

			} 
			else if(nodes.get(0).getOp().equals("CHAR_CONST")){
				node.setType("char");

			} 
			else if(nodes.get(0).getOp().equals("STRING_CONST")){
				node.setType("string");

			} 
			else if(nodes.get(0).getOp().equals("BOOL_CONST")){
				node.setType("boolean");

			} 
			else if(nodes.get(0).getOp().equals("DOUBLE_CONST")){
				node.setType("double");
			} 
			else if(nodes.get(0).getOp().equals("ID")){ //intercetto i casi in cui es: int i=j;

				Leaf l = (Leaf) nodes.get(0);
				EntryInfo ei = stack.lookup(l.getVal());
				node.setType(ei.getType());
			} 
			else if(nodes.get(0).getOp().equals("AddOp") || nodes.get(0).getOp().equals("DiffOp") || nodes.get(0).getOp().equals("DivOp") || nodes.get(0).getOp().equals("MulOp")){
				//se InizializationOp asssegna una espressione mathOp ese: i=i+2;
				nodes.get(0).accept(this);//visita del mathOp dell assegnazione in dichiarazione sotto head
				node.setType(nodes.get(0).getType());			
			} 
		}
		else {
			for (Node n : nodes){
				if (n!=null){
					n.accept(this);
					n.setType(type);
				}
			}
			if(node.getOp().equals("CompStatOp")) {
				node.setType("no-type");
			}

		}
		return null;
	}

	public Object visit(Leaf node) throws Exception {
		/*dato che in leaf entrano altre getOp faccio il controllo sull id
		per verificare se esiste la variabile o la funzione*/
		if(node.getOp().equals("ID")){  
			EntryInfo ei=stack.lookup(node.getVal());
			node.setType(ei.getType());


		} 

		return null;
	}




	/*poichè esistono solo 2 scope(ProgramOp e procDeclOp) vado a settare prima lo scope di ProgramOp*/

	public Object visit(ProgramOp node) throws Exception {
		//	System.out.println("VISITA program");
		stack.addIdentifier("HEAD", new EntryInfo("Program"));
		SymbolTable st = new SymbolTable("Programma");

		stack.push(st);
		//setto la symboltable della classe ScopeNode per mantenere il riferimento(usato per type checking)
		node.setSymbolTable(st); 
		for(Node n:node.nodeList()) {
			if(n!=null){
				n.accept(this);
			}
		}
		stack.pop(); //rilascio quando ho visitato tutti i figli
		System.out.println("SymbolTable: " + node.getSymbolTable());
		return null;
	}

	public Object visit(ProcDeclOp node) throws Exception {
		//System.out.println("VISITA procDeclOp ");

		stack.addIdentifier(node.getName(), new EntryInfo("Function"));
		SymbolTable st = new SymbolTable(node.getName());
		stack.push(st);
		//setto la symboltable della classe ScopeNode per mantenere il riferimento della funzione(usato per type checking)
		node.setSymbolTable(st);
		functionName= node.getName();
		node.nodeList().get(0).accept(this); //accept nome funzione

		if(node.nodeList().size()==3){//puo avere IdFunzione(nome funzione), parametri, body
			if(node.nodeList().get(1)!=null) {//perchè parametri In possono non esserci
				node.nodeList().get(1).accept(this); //accept parametri  funzione
			}
			functionName=null;
			node.nodeList().get(2).accept(this); //accept body funzione
		}

		else{//funzione senza parametri
			if(node.nodeList().get(1)!=null) { //perchè parametri In possono non esserci
				node.nodeList().get(1).accept(this); //accept body funzione senza parametri

			}
			functionName=null;

		}
		stack.pop();//rilascio quando ho visitato tutti i figli
		System.out.println("SymbolTable: " + node.getSymbolTable());
		return null;
	}

	//faccio una visita delle dichiarazioni di variabili(VarInitOp)
	public Object visit(VarDeclOp node) throws Exception {

		//System.out.println("VISITA varDeclOp");

		type= node.getLeafType();  //prendo il tipo di varDeclOp

		Node toVisit= node.getNode();   //prendo l'ogetto VarInitOp
		if(toVisit != null) {
			toVisit.accept(this);
		}
		
		return null;
	
	}

	//visita dei VarInitOp
	public Object visit(VarInitOp node) throws Exception {

		//System.out.println("VISITA varInitop");
		ArrayList<Node> toVisit= node.nodeList();
		Leaf l ;
		
		//parto da 0 e incremento 2 alla volta perchè rappresenta la variabile senza inizializzazione es int i;
		for(int i=0;i<toVisit.size();i=i+2) { //analizzo solamente l' ID di un eventuale inizialization 
			l = (Leaf)toVisit.get(i);
			stack.addIdentifier(l.getVal(), new EntryInfo("Variable", type));
			toVisit.get(i).accept(this);
		}


		String secondOpType="";
		//parto da 1 e incremento 2 alla volta perchè rappresenta la variabile con inizializzazione es int i=0;
		for(int j=1;j<toVisit.size();j=j+2) {//vedo se c'è mismatch

			if(toVisit.get(j)!=null) {

				toVisit.get(j).accept(this);//visita solo per OpNode (expr dell'inizializationOp)
				secondOpType=toVisit.get(j).getType();
				if( (type.equals("boolean") && secondOpType.equals("boolean"))||  
						(type.equals("integer") && secondOpType.equals("integer"))||
						(type.equals("double") && secondOpType.equals("double")) ||
						(type.equals("double") && secondOpType.equals("integer"))|| 
						(type.equals("char") && secondOpType.equals("char")) ||
						(type.equals("string") && secondOpType.equals("string")) ||
						(type.equals("string") && secondOpType.equals("char")))     
					node.setType("no-type");
				else
					throw new Exception("Type Mismatch");	
			}
		}


		return null;
	}

	public Object visit(ParOp node) throws Exception {
		//System.out.println("VISITA parOp");

		ArrayList<Node> toVisit= node.nodeList();
		Leaf l ;
		int position=0;
		Paramater p = new Paramater();

		for(int i=2;i<toVisit.size();i=i+3) {
			l = (Leaf)toVisit.get(i);
			type = toVisit.get(i-1).getType();
			stack.addIdentifier(l.getVal(), new EntryInfo("Variable", type));//setto i parametri nella symbolstack dello scope della funzione
			if(functionName!=null){		
				this.functionTable.setNomeFunzione(functionName);//setto il nome della tabella nella FunctionTable che contiene le funzioni con i parametri

				if(toVisit.get(i-2).getOp().equals("in")) {//setto i vari parametri a seconda se sono parametri di in o di out
					p= new  Paramater(type, position);
					position++;
					this.functionTable.addParamInFunction(functionName, p);
				}
				else if  (toVisit.get(i-2).getOp().equals("out")) {
					p= new  Paramater(type, position);
					position++;

					this.functionTable.addParamOutFunction(functionName,p);}

				else if (toVisit.get(i-2).getOp().equals("inout")) {
					p= new  Paramater(type, position);
					position++;
					this.functionTable.addParamInOutFunction(functionName, p);}

			}
		}

		return null;
	}

	public Object visit(MathOp node) throws Exception {
		//System.out.println("MATHOP");

		node.getFirstExpr().accept(this);
		node.getSecondExpr().accept(this);




		String operation= node.getOp();

		if((operation.equals("AndOp"))|| (operation.equals("OrOp"))){
			if((node.getFirstExpr().getType().equals("boolean"))&&(node.getSecondExpr().getType().equals("boolean")) ) 
				node.setType("boolean");
			else{


				throw new Exception("Type Mismatch AND/OP");
			}
		}

		else if((operation.equals("AddOp"))|| (operation.equals("DiffOp")|| (operation.equals("MulOp")||
				(operation.equals("DivOp"))))) 
		{

			if(node.getFirstExpr().getType().equals("integer") && node.getSecondExpr().getType().equals("integer")  ) {
				node.setType("integer");}

			else if((node.getFirstExpr().getType().equals("double") && node.getSecondExpr().getType().equals("double")  )||
					(node.getFirstExpr().getType().equals("double") && node.getSecondExpr().getType().equals("integer")  )||
					(node.getFirstExpr().getType().equals("integer") && node.getSecondExpr().getType().equals("double")  ))
				node.setType("double");


			else
				throw new Exception("Type Mismatch ARITH_OP");


		}

		else {//relop

			if((node.getFirstExpr().getType().equals("integer") && node.getSecondExpr().getType().equals("integer"))||
					(node.getFirstExpr().getType().equals("double") && node.getSecondExpr().getType().equals("integer")) ||  
					(node.getFirstExpr().getType().equals("integer") && node.getSecondExpr().getType().equals("double")) || 
					(node.getFirstExpr().getType().equals("double") && node.getSecondExpr().getType().equals("double")) ||
					(node.getFirstExpr().getType().equals("string") && node.getSecondExpr().getType().equals("string"))||
					(node.getFirstExpr().getType().equals("char") && node.getSecondExpr().getType().equals("char")) ||
					(node.getFirstExpr().getType().equals("boolean") && node.getSecondExpr().getType().equals("boolean"))){


				node.setType("boolean");



			}
			else 

				throw new Exception("Type Mismatch RELOP");


		}



		return null;
	}

	public Object visit(SingleOp node) throws Exception {
		//System.out.println("VISITA SingleOp");

		String operation= node.getOp();

		if( operation.equals("NotOp") ) {
			ArrayList<Node>toVisit = node.nodeList();
			for (int i=0; i<toVisit.size(); i++){
				toVisit.get(i).accept(this);
			}


			String nodeType= node.nodeList().get(0).getType();
			if( nodeType.equals("boolean")) {
				node.setType(nodeType);
			}else {

				throw new Exception("Type Mismatch");
			}

		}else { //se � UminusOp
			String nodeType= node.nodeList().get(0).getType();
			if( (nodeType.equals("integer")) || (nodeType.equals("double")) ) {
				node.setType(nodeType);

			}else {
				throw new Exception("Type Mismatch");
			}
		}
		return null;
	}

	public Object visit(ConditionalOp node) throws Exception {
		//System.out.println("VISITA conditionalOp ");

		ArrayList<Node> nodes = node.nodeList();
		Node first= nodes.get(0);
		first.accept(this);

		if(first.getType().equals("boolean")) {
			node.setType("no-type");
		}else {
			throw new Exception("Type Mismatch");
		}
		for (int i=1; i<nodes.size(); i++){//per Compstat
			nodes.get(i).accept(this);
		}

		return null;
	}

	public Object visit(IfThenOp node) throws Exception {
		//System.out.println("IfThenOp");
		return this.visit((ConditionalOp) node);

	}

	public Object visit(IfThenElseOp node) throws Exception {
		//System.out.println("IfThenOpelseOp");
		return this.visit((ConditionalOp) node);
	}

	public Object visit(WhileOp node) throws Exception {
		//System.out.println("WhileOp");
		return this.visit((ConditionalOp) node);
	}

	public Object visit(CallOp node) throws Exception {

		stack.lookup(node.getName()); //controllo se esiste la firma della funzione


		if(node.nodeList().size()==2){//se ==2 significa che ci sono i parametri senno' nn devo svolgerlo altrimenti error

			ArrayList<Node> args= ((OpNode)node.nodeList().get(1)).nodeList();//parametri della funzione chiamata(args di callop)


			ArrayList<Paramater> inParameters= functionTable.getParamIn(node.getName());//parametri in funzione
			ArrayList<Paramater> outParameters= functionTable.getParamOut(node.getName());//parametri out funzione
			ArrayList<Paramater> inOutParameters= functionTable.getParamInOut(node.getName());//parametri inout funzione
			int argumentsNumber=inOutParameters.size()+inParameters.size()+outParameters.size();


			if( args.size()!= argumentsNumber ) {
				throw new Exception("Illegal Number Of Arguments In Function " + node.getName().toUpperCase());
			}

			for(int j=0; j<args.size(); j++) {

				args.get(j).accept(this);//accept per vedere se sono dichiarate le variabili di cALLop

				for(int k=0; k<inParameters.size();k++) {//un for per ogni array di parameter (in out e inout)
					if(j==inParameters.get(k).getPositionType()){
						if( !args.get(j).getType().equals(inParameters.get(k).getParameterType()) ) {
							throw new Exception("Type Mismatch In Function " + node.getName().toUpperCase() + " Param In");

						}

					}

				}
				for(int k=0; k<outParameters.size();k++) {//un for per ogni array di parameter (in out e inout)
					if(j==outParameters.get(k).getPositionType()){
						if( !args.get(j).getType().equals(outParameters.get(k).getParameterType()) ) {
							throw new Exception("Type Mismatch In Function " + node.getName().toUpperCase() + " ParamOut");

						}

					}

				}

				for(int k=0; k<inOutParameters.size();k++) {//un for per ogni array di parameter (in out e inout)
					if(j==inOutParameters.get(k).getPositionType()){
						if( !args.get(j).getType().equals(inOutParameters.get(k).getParameterType()) ) {
							throw new Exception("Type Mismatch In Function " + node.getName().toUpperCase() + " Param InOut");

						}

					}

				}

			}
		}

		return null;
	}

	public Object visit(AssignOp node) throws Exception {
		EntryInfo ei= stack.lookup(node.getName());
		node.getFirstExpr().setType(ei.getType());
		node.getSecondExpr().accept(this);

		String firstOpType=node.getFirstExpr().getType();
		String secondOpType=node.getSecondExpr().getType();


		if( (firstOpType.equals("boolean") && secondOpType.equals("boolean"))||  
				(firstOpType.equals("integer") && secondOpType.equals("integer"))||
				(firstOpType.equals("double") && secondOpType.equals("double")) ||
				(firstOpType.equals("double") && secondOpType.equals("integer"))|| 
				(firstOpType.equals("char") && secondOpType.equals("char")) ||
				(firstOpType.equals("string") && secondOpType.equals("string")) ||
				(firstOpType.equals("string") && secondOpType.equals("char")))     

			node.setType("no-type");
		else
			throw new Exception("Type Mismatch");	
		return null;
	}

	public Object visit(WriteOp node) throws Exception {
		//System.out.println("VISITA WriteOp ");



		OpNode args= (OpNode) node.nodeList().get(0); //args formato da stringConst, e varie variabili

		ArrayList<Node> nodes= args.nodeList();	


		if(nodes != null) {
			for(Node n:nodes) {
				if(n!=null ){

					n.accept(this);

				}
			}
		}
		return null;

	}

	public Object visit(ReadOp node) throws Exception {

		ArrayList<Leaf> inputs=new ArrayList<Leaf>();
		OpNode varOp=(OpNode) node.nodeList().get(0);
		int i=0;
		while(i<varOp.getSize()) {
			inputs.add((Leaf)varOp.nodeList().get(i));
			i++;
		}

		for(int j=0; j<inputs.size(); j++){

			//stack.lookup(inputs.get(j).getVal());

			EntryInfo ei = stack.lookup(inputs.get(j).getVal());

			node.setType(ei.getType());

		}
		ArrayList<Node> nodes=varOp.nodeList();
		if(nodes != null) {
			for(Node n:nodes) {
				if(n!=null ){

					n.accept(this); // perchè se non faccio questa visita non setto il tipo?

				}
			}
		}


		return null;
	}

	public Object visit(UseNode node) throws Exception {
		return null;
	}


	public Object visit(ScopeNode node) throws Exception {
		return null;
	}

	@Override
	public Object visit(ForOp node) throws Exception {
		
		stack.addIdentifier("FOR", new EntryInfo("For"));
		SymbolTable st = new SymbolTable("FOR");
		stack.push(st);
		//setto la symboltable della classe ScopeNode per mantenere il riferimento del for(usato per type checking)
		node.setSymbolTable(st);
		
		node.nodeList().get(0).accept(this);
		node.nodeList().get(1).accept(this);
		node.nodeList().get(2).accept(this);
		node.nodeList().get(3).accept(this);
		
		//System.out.println("assignop type:"+node.nodeList().get(0).getType()+ " expr1:"+node.nodeList().get(1).getType()+ " expr2:"+node.nodeList().get(2).getType());
		
		if(!(node.nodeList().get(0).getType().equals("no-type") && node.nodeList().get(1).getType().equals("boolean") && node.nodeList().get(2).getType().equals("no-type")))
			throw new Exception("Type Mismatch ---");
		
		
		stack.pop();//rilascio quando ho visitato tutti i figli
		System.out.println("SymbolTable: " + node.getSymbolTable());
		
		return null;

	}

	@Override
	public Object visit(IncrementsOp node) throws Exception {
		
		node.nodeList().get(0).accept(this);
		
		
		node.setType("no-type");
		
		
		
		
		if(!node.nodeList().get(0).getType().equals("integer"))
			throw new Exception("Type Mismatch");	
		//System.out.println("Operation: "+node.getOp()+" type: "+node.getType());
		
		
		return null;
	}

	@Override
	public Object visit(ModOp node) throws Exception {
		// TODO Auto-generated method stub
		
		Leaf expr1=(Leaf)node.getFirstExpr();
		Leaf expr2=(Leaf)node.getSecondExpr();
		
		expr1.accept(this);
		expr2.accept(this);


		node.setType("no-type");

		String operation= node.getOp();

		if(operation.equals("ModOp"))
		{
			if((expr1.getType().equals("integer"))&&(expr2.getType().equals("integer")) ) 
				node.setType("integer");
			else
			{
				throw new Exception("Type Mismatch - Mod operator only for integers" );
			}
		}
		
		//System.out.println("Operation: "+node.getOp()+" type: "+node.getType());
		
		return null;
	}

	@Override
	public Object visit(DecreaseOp node) throws Exception {
		
		node.nodeList().get(0).accept(this);
		if(!node.nodeList().get(0).getType().equals("integer"))
			throw new Exception("Type Mismatch - Decrease operator only for integers" );
		node.setType("no-type");
		
		//System.out.println("Operation: "+node.getOp()+" type: "+node.getType());

		return null;
	}

	@Override
	public Object visit(SwitchOp node) throws Exception {
		
		//Visito il leaf dello switch op - sarebbe un Expr
		node.nodeList().get(0).accept(this);
		switch_type=node.nodeList().get(0).getType();
		
		
		//Visito la lista dei caseListOp
		node.nodeList().get(1).accept(this);
		
		return null;
	}

	@Override
	public Object visit(CaseOp node) throws Exception {
		
		ArrayList<Node> nodeList=node.nodeList();
		
		if(nodeList != null) {
			for(Node n:nodeList) {
				if(n!=null ){

					n.accept(this); 

				}
			}
		}
		
		if(!node.nodeList().get(0).getType().equals(switch_type))
			throw new Exception("Type Mismatch in SwitchOp" );
		
		return null;
	}
	
	@Override
	public Object visit(CaseListOp node) throws Exception
	{
		ArrayList<Node> nodeList=node.nodeList();
		if(nodeList != null) {
			for(Node n:nodeList) {
				if(n!=null ){

					n.accept(this); 

				}
			}
		}
		return null;
	}	
	

	@Override
	public Object visit(DefaultOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		node.nodeList().get(1).accept(this);
		return null;
	}

	@Override //Dichiaro l'array, aggiungendolo alla tabella dei simboli come Array --> int a[3]; sotto HEAD
	public Object visit(ArrayOp node) throws Exception {
		
		
		node.nodeList().get(0).accept(this);
		
		ArrayIndexOp id=(ArrayIndexOp)node.nodeList().get(1);
		Leaf var=(Leaf)id.getFirstExpr();
		stack.addIdentifier(var.getVal(), new EntryInfo("Array", node.nodeList().get(0).getType()));
		var.accept(this);
		
		
		return null;
	}

	@Override //Operazioni con gli array, l'assegnamento è valido solo tra tipi uguali.
	public Object visit(ArrayAssignOp node) throws Exception {
		//Node arrayindex, Node expr questo ricevo in input
		node.nodeList().get(0).accept(this);
		node.nodeList().get(1).accept(this);
		
	
		//System.out.println(id.getType()+" -- "+node.nodeList().get(2).getType());
		if(!node.nodeList().get(0).getType().equals(node.nodeList().get(1).getType()))
			throw new Exception("Type Mismatch in Array assignop");
		return null;
	}

	@Override
	//Visit per la posizione dell'array es. a[45]; in input: variabile e int_const "indice"
	public Object visit(ArrayIndexOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		node.nodeList().get(1).accept(this);
		node.setType(node.nodeList().get(0).getType());
		return null;
	}

	@Override
	public Object visit(StructOp node) throws Exception {
		
		
		Leaf name_struct=(Leaf)node.nodeList().get(0);
		stack.addIdentifier(name_struct.getVal(), new EntryInfo("Struct"));
		SymbolTable st = new SymbolTable(name_struct.getVal());
		stack.push(st);
		node.setSymbolTable(st);
		
		name_struct.accept(this);
		node.nodeList().get(1).accept(this);
		
		
		stack.pop();//rilascio quando ho visitato tutti i figli
		System.out.println("SymbolTable: " + node.getSymbolTable());
		
		return null;
	}

	@Override
	public Object visit(StructInitOp node) throws Exception {
		for(int i=0;i<node.nodeList().size();i++)
		{
			node.nodeList().get(i).accept(this);
		}
		return null;
	}

	@Override
	public Object visit(InternalDefOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		return null;
	}

		
	

}