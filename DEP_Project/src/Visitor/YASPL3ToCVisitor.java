package Visitor;

import java.io.FileWriter;
import java.util.ArrayList;

import FunctionUtility.FunctionArgs;
import FunctionUtility.FunctionTable;
import FunctionUtility.Paramater;
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

public class YASPL3ToCVisitor implements Visitor {
	private String nomeFile;
	private FileWriter fileOutput;
	private FunctionTable functionTable;
	private String functionName=null;
	private boolean writeYes = false;
	private boolean forYes = false;
	private boolean internal=false;

	private ArrayList<String> inputOutOrInout;


	private ArrayList<String>   saveVar;

	public YASPL3ToCVisitor(String nomeFile) {
		this.nomeFile= nomeFile;
		this.functionTable= new FunctionTable("");
		saveVar= new ArrayList<>();
		inputOutOrInout=new ArrayList<>();
	}

	public Object visit(Node node) {
		return null;
	}

	public Object visit(OpNode node) throws Exception {


		if(node.getOp().equals("InitializationOp")){ //se sono inizializzazioni metto = 

			fileOutput.write("=");



		}

		ArrayList<Node> nodes = node.nodeList();
		for (Node n : nodes){
			if (n!=null){

				n.accept(this);


			}
		}

		return null;
	}

	public Object visit(Leaf node) throws Exception {		
		System.out.println("sono in leaf");

		System.out.println("________________LEAF_________________" +node.getVal()+ " " +node.getType()+" "+node.getOp() );





		if(node.getOp().equals("INT_CONST")){

			fileOutput.write(node.getVal());

		} 
		else if(node.getOp().equals("CHAR_CONST")){
			if(node.getVal().equals(" ")){
				fileOutput.write("' '");

			}
			else{
				fileOutput.write("'");
				fileOutput.write(node.getVal());
				fileOutput.write("'");
			}

		} 
		else if(node.getOp().equals("STRING_CONST")){
			if(!writeYes){

				fileOutput.write(node.getVal());
			}

		} 
		else if(node.getOp().equals("BOOL_CONST")){
			fileOutput.write(node.getVal());

		} 
		else if(node.getOp().equals("DOUBLE_CONST")){
			fileOutput.write(node.getVal());

		}
		else if(node.getOp().equals("ID") && functionName==null ){

			//System.out.println("fuori "+writeYes);


			for(int i=0;i<inputOutOrInout.size();i++){
				if(inputOutOrInout.get(i).equals(node.getVal())){
					fileOutput.write("*"+node.getVal());
					return null;
				}


			}


			if(writeYes) {
				//System.out.println("dentro "+writeYes+" "+node.getVal());
				saveVar.add(node.getVal());

			}
			else{


				fileOutput.write(node.getVal());
			}


		}
		return null; 
	}

	@Override
	public Object visit(ProgramOp node) throws Exception {
		System.out.println("sono in ProgramOp");
		fileOutput= new FileWriter(nomeFile + ".c");
		String toReturn = "#include <stdio.h>\n";
		toReturn += "#include <stdbool.h>\n";
		toReturn += "#include <string.h>\n";
		toReturn += "#include <ctype.h>\n";


		fileOutput.write(toReturn);




		ArrayList<Node> decls= ((OpNode) node.nodeList().get(0)).nodeList();
		ArrayList<Node> stats= ((OpNode) node.nodeList().get(1)).nodeList();

		//visita delle varie dichiarazioni decls
		for(Node n:decls) {
			n.accept(this);
		}

		fileOutput.write("int main(void){\n");
		//visita sui vari statements stats
		for(Node n:stats) {
			n.accept(this);
		}

		toReturn = "\n return 0;\n}";
		fileOutput.write(toReturn);
		fileOutput.close();
		return null;

	}


	public Object visit(VarDeclOp node) throws Exception {
		System.out.println("sono in VarDecl");
		boolean string=false;
		String type= node.getLeafType();//prendo type di ogni dichiarazione di variabile

		if( type.equals("integer") ) {
			type= "int";
		}else if(type.equals("boolean")){
			type= "bool";
		}
		else if(type.equals("double")){
			type= "double";
		}
		else if(type.equals("string")){
			string=true;
			type= "char";
		}
		else if(type.equals("char")){
			type= "char";
		}

		fileOutput.write(type + " ");
		Node toVisit= node.getNode();
		if(toVisit != null) {//mi visito i varInitOp uno alla volta
			toVisit.accept(this);
		}
		if(string==true){
			fileOutput.write(" [50]");
		}

		fileOutput.write(";\n");//dopo aver visitato varInitOp variabile verra messo il ;
		return null;
	}

	public Object visit(VarInitOp node) throws Exception {
		System.out.println("sono in VarInitOp");

		ArrayList<Node> varInitOp= node.nodeList();

		for(int i =0; i<varInitOp.size();i++) { //mi scorro i varinit op 
			if(varInitOp.get(i)!=null){
				if(i>1 && !varInitOp.get(i).getOp().equals("InitializationOp"))
					fileOutput.write(",");


				varInitOp.get(i).accept(this);//visit dell'opnode che poi andr� nel Leaf a fare la visita che mi dara il valore assegnato alla varibile


			}


		}

		return null;
	}

	@Override
	public Object visit(ProcDeclOp node) throws Exception {
		boolean parOp=false;
		System.out.println("sono in ProcDeclOp");
		functionName= node.getName();

		fileOutput.write("void "+node.getName()+"(");//stampa solo nome funzione
		ArrayList<Node> functionSignature = node.nodeList();
		for(Node n:functionSignature) {
			n.accept(this);//visita di parametri e corpo funzione (parOp e OpNode=body)
			if(n.getOp().equals("ParOp")){
				parOp=true;
			}
		}


		if(parOp==false){
			fileOutput.write("){\n");
		}else

			fileOutput.write("}");

		if(internal)
			fileOutput.write(";\n");
		else
			fileOutput.write("\n");

		return null;
	}


	public Object visit(ParOp node) throws Exception {
		System.out.println("sono in ParOp");
		ArrayList<Node> parameters= node.nodeList();
		int position=0;
		Paramater p = new Paramater();
		String type;
		Leaf l;
		for(int i=2;i<parameters.size();i=i+3) {





			type = parameters.get(i-1).getType();
			if(type.equals("integer")){
				type="int";
			}

			if(parameters.get(i-2).getOp().equals("in")) {//setto i vari parametri a seconda se sono parametri di in o di out
				p= new  Paramater(type, position);
				position++;
				this.functionTable.addParamInFunction(functionName, p);
				if (i==2) {
					l = (Leaf)parameters.get(i);
					fileOutput.write(type+" "+l.getVal());
				}else {
					l = (Leaf)parameters.get(i);
					fileOutput.write(", "+type+" "+l.getVal());
				}

			}
			else if  (parameters.get(i-2).getOp().equals("out")) {
				p= new  Paramater(type, position);
				position++;


				this.functionTable.addParamOutFunction(functionName,p);
				if (i==2) {
					l = (Leaf)parameters.get(i);
					fileOutput.write(type+" *"+l.getVal());
					inputOutOrInout.add(l.getVal());// mi serve sapere chi è out
				}else {
					l = (Leaf)parameters.get(i);
					fileOutput.write(", "+type+" *"+l.getVal());
					inputOutOrInout.add(l.getVal());//mi serve sapere chi è out
				}	
			}

			else if (parameters.get(i-2).getOp().equals("inout")) {
				p= new  Paramater(type, position);
				position++;
				this.functionTable.addParamInOutFunction(functionName, p);
				if (i==2) {
					l = (Leaf)parameters.get(i);
					fileOutput.write(type+" *"+l.getVal());
					inputOutOrInout.add(l.getVal());//mi serve sapere chi è inout

				}else {
					l = (Leaf)parameters.get(i);
					fileOutput.write(", "+type+" *"+l.getVal());
					inputOutOrInout.add(l.getVal());//mi serve sapere chi è inout
				}		
			}

		}

		functionName = null;
		fileOutput.write("){\n");


		return null;
	}

	public Object visit(MathOp node) throws Exception {
		System.out.println("sono in MathOp");
		String operator= node.getOp();

		switch (operator) {
		case "AddOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" + ");
			node.nodeList().get(1).accept(this);
			break;
		case "DiffOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" - ");
			node.nodeList().get(1).accept(this);
			break;
		case "MulOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" * ");
			node.nodeList().get(1).accept(this);
			break;
		case "DivOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" / ");
			node.nodeList().get(1).accept(this);
			break;
		case "AndOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" && ");
			node.nodeList().get(1).accept(this);
			break;
		case "OrOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" || ");
			node.nodeList().get(1).accept(this);
			break;
		case "GtOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" > ");
			node.nodeList().get(1).accept(this);
			break;
		case "GeOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" >= ");
			node.nodeList().get(1).accept(this);
			break;
		case "LtOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" < ");
			node.nodeList().get(1).accept(this);
			break;
		case "LeOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" <= ");
			node.nodeList().get(1).accept(this);
			break;
		case "EqOp":
			node.nodeList().get(0).accept(this);
			fileOutput.write(" == ");
			node.nodeList().get(1).accept(this);
			break;

		default: 
			break;
		}


		return null;
	}

	@Override
	public Object visit(SingleOp node) throws Exception {
		System.out.println("sono in single node");
		if( node.getOp().equals("UminusOp") ) {
			fileOutput.write("-");
			node.nodeList().get(0).accept(this);
		}

		if( node.getOp().equals("NotOp") ) {
			fileOutput.write("!");
			node.nodeList().get(0).accept(this);

		}

		return null;
	}

	@Override
	public Object visit(ConditionalOp node) throws Exception {
		return null;
	}

	@Override
	public Object visit(IfThenOp node) throws Exception {


		Node expression=node.nodeList().get(0);
		Node compstat=node.nodeList().get(1);

		fileOutput.write("if( ");
		expression.accept(this);
		fileOutput.write(") {\n");

		compstat.accept(this);
		fileOutput.write("}\n");
		return null;
	}

	@Override
	public Object visit(IfThenElseOp node) throws Exception {
		Node expression= node.nodeList().get(0);
		Node compstat1= node.nodeList().get(1);
		Node compStat2= node.nodeList().get(2);

		fileOutput.write("if( ");
		expression.accept(this);
		fileOutput.write(" ){\n");
		compstat1.accept(this);
		fileOutput.write("}\nelse{\n");
		compStat2.accept(this);
		fileOutput.write("}\n");
		return null;
	}

	@Override
	public Object visit(WhileOp node) throws Exception {
		Node expression= node.nodeList().get(0);
		Node compstat= node.nodeList().get(1);

		fileOutput.write("while( ");
		expression.accept(this);
		fileOutput.write(" ){\n");

		compstat.accept(this);

		fileOutput.write("}\n");
		return null;
	}

	@Override
	public Object visit(CallOp node) throws Exception {
		FunctionArgs fargs= new FunctionArgs();

		System.out.println("SONO IN CALLOP");
		Node nameCall=  node.nodeList().get(0); // nome funzione
		Leaf l=(Leaf)nameCall;





		fileOutput.write(l.getVal()+"(");
		OpNode args = (OpNode)node.nodeList().get(1); // args funzione
		ArrayList<Node> nodes= args.nodeList();	

		fargs=functionTable.get(l.getVal());

		for(int i=0;i<nodes.size();i++){

			for(int j=0;j<fargs.getInputParameters().size();j++){
				if(i==fargs.getInputParameters().get(j).getPositionType()){
					nodes.get(i).accept(this);

				}
			}





			for(int j=0;j<fargs.getInOutParameters().size();j++){

				if(i==fargs.getInOutParameters().get(j).getPositionType()){
					if(fargs.getInOutParameters().size()==1){
						fileOutput.write(" &");
						nodes.get(i).accept(this);
					}

				}
			}

			for(int j=0;j<fargs.getOutputParameters().size();j++){
				if(i==fargs.getOutputParameters().get(j).getPositionType()){
					fileOutput.write(" &");
					nodes.get(i).accept(this);

				}
			}

			if(nodes.size()>1 && i!=nodes.size()-1){

				fileOutput.write(",");
			}


		}








		fileOutput.write(");");
		fileOutput.write("\n");
		return null;
	}

	@Override
	public Object visit(AssignOp node) throws Exception {
		System.out.println("sono in Assignop");
		int i=0;
		ArrayList<Node> nodes= node.nodeList();


		for(Node n:nodes){ 

			if(n!=null){
				i++;
				n.accept(this);
			}



			if(i==1){
				fileOutput.write("=");
			}

		}


		if(!forYes)
			fileOutput.write(";\n");
		return null;
	}

	public Object visit(WriteOp node) throws Exception {
		System.out.println("sono in writeOp");




		OpNode args= (OpNode) node.nodeList().get(0); //args formato da stringConst, e varie variabili
		ArrayList<Node> nodes= args.nodeList();	


		Leaf l;
	if(nodes.size()>=1){
	
			fileOutput.write("printf(\" ");
		
	}
		for(int j=0;j<nodes.size();j++){
	
			
			if(nodes.get(j).getOp().equals("STRING_CONST")&& nodes.size()>=1) {
				writeYes=true;

				l = (Leaf)nodes.get(j);
				fileOutput.write(" " + l.getVal()+" ");
			}

			else  {

				writeYes=true;

				if(nodes.size()>=1){



					if( nodes.get(j).getType().equals("integer") || nodes.get(j).getType().equals("boolean")) {
						fileOutput.write(" %d");
					}else if (nodes.get(j).getType().equals("string")) {
						fileOutput.write(" %s");
					}else if (nodes.get(j).getType().equals("char")) {
						fileOutput.write(" %c");
					}else if (nodes.get(j).getType().equals("double")) {
						fileOutput.write(" %lf");
					}
				}


				nodes.get(j).accept(this);
			}
		}	




		
		fileOutput.write(" \\n \"");

		for(int i=0;i<saveVar.size();i++){

			if(saveVar.size()>=1 && nodes.size()>=1){

				fileOutput.write(","+saveVar.get(i));

			}


		}
		saveVar=new ArrayList<String>();


		fileOutput.write(");\n");
		writeYes=false;
		return null;
	}

	@Override
	public Object visit(ReadOp node) throws Exception {

		System.out.println("sono in ReadOp");
		ArrayList<String> varRead=new ArrayList<String>();	

		fileOutput.write("scanf(\" ");
		OpNode vars= (OpNode) node.nodeList().get(0); //vgrs
		ArrayList<Node> nodes= vars.nodeList();	
		ArrayList<String>typ= new ArrayList<>();


		Leaf l;
		for(Node n: nodes) {
			if(n!=null){

				l=(Leaf)n;
				varRead.add(l.getVal());

				if(l.getType().equals("integer")){
					fileOutput.write("%d" );
					typ.add("integer");
				}
				else 
					if(l.getType().equals("double")){
						fileOutput.write("%lf" );
						typ.add("double");
					}
					else
						if(l.getType().equals("string")){
							typ.add("string");
							fileOutput.write("%s" );
						}
						else if(l.getType().equals("char")){
							typ.add("char");
							fileOutput.write( " %c" );
						}

			}
		}

		fileOutput.write("\"" );
		for(int i=0;i<varRead.size();i++){
			if(typ.get(i).equals("string")){
				fileOutput.write(", "+varRead.get(i) );

			}
			else
				fileOutput.write(", &"+varRead.get(i) );

		}
		fileOutput.write(");\n");

		return null;
	}
	public Object visit(UseNode node) throws Exception {
		// TODO Auto-generated me thod stub
		return null;
	}

	public Object visit(ScopeNode node) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ForOp node) throws Exception {
		ArrayList<Node> nodes= node.nodeList();
		forYes=true;
		fileOutput.write("for(");
		nodes.get(0).accept(this);
		fileOutput.write(";");
		nodes.get(1).accept(this);
		fileOutput.write(";");
		nodes.get(2).accept(this);
		fileOutput.write("){\n");
		nodes.get(3).accept(this);
		forYes=false;
		fileOutput.write("}\n");




		return null;
	}

	@Override
	public Object visit(IncrementsOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		fileOutput.write("++;\n");
		return null;
	}

	@Override
	public Object visit(ModOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		fileOutput.write("%");
		node.nodeList().get(1).accept(this);
		return null;
	}

	@Override
	public Object visit(DecreaseOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		fileOutput.write("--;\n");
		return null;
	}

	@Override
	public Object visit(SwitchOp node) throws Exception {
		fileOutput.write("switch(");
		node.nodeList().get(0).accept(this);
		fileOutput.write("){\n");
		node.nodeList().get(1).accept(this);
		fileOutput.write("}\n");
		return null;
	}

	@Override
	public Object visit(CaseOp node) throws Exception {
		
		fileOutput.write("case ");
		node.nodeList().get(0).accept(this);
		fileOutput.write(":\n");
		node.nodeList().get(1).accept(this);
		node.nodeList().get(2).accept(this);
		fileOutput.write("break;\n");
			
		return null;
	}
	
	@Override
	public Object visit(CaseListOp node) throws Exception {
		for(int i=0;i<node.nodeList().size();i++)
		{
			node.nodeList().get(i).accept(this);
		}
		return null;
	}

	@Override
	public Object visit(DefaultOp node) throws Exception {
		fileOutput.write("default:\n");
		node.nodeList().get(0).accept(this);
		node.nodeList().get(1).accept(this);
		fileOutput.write("break;\n");
		return null;
	}

	@Override
	public Object visit(ArrayOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		String type=null;
		
		if( node.nodeList().get(0).getType().equals("integer") ) {
			type= "int";
		}else if(node.nodeList().get(0).getType().equals("boolean")){
			type= "bool";
		}
		else if(node.nodeList().get(0).getType().equals("double")){
			type= "double";
		}
		else if(node.nodeList().get(0).getType().equals("string")){
			type= "char";
		}
		else if(node.nodeList().get(0).getType().equals("char")){
			type= "char";
		}

		
		fileOutput.write(type+" ");
		node.nodeList().get(1).accept(this);
		fileOutput.write(";\n");
		return null;
	}

	@Override
	public Object visit(ArrayAssignOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		fileOutput.write("=");
		node.nodeList().get(1).accept(this);
		fileOutput.write(";\n");
		return null;
	}

	@Override
	public Object visit(ArrayIndexOp node) throws Exception {
		node.nodeList().get(0).accept(this);
		fileOutput.write("[");
		node.nodeList().get(1).accept(this);
		fileOutput.write("]");
		return null;
	}

	@Override
	public Object visit(StructOp node) throws Exception {
		fileOutput.write("struct ");
		node.nodeList().get(0).accept(this);
		fileOutput.write("{\n");
		node.nodeList().get(1).accept(this);
		fileOutput.write("};\n");
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
		internal=true;
		node.nodeList().get(0).accept(this);
		internal=false;
		return null;
	}

	
}
