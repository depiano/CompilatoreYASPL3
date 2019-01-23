package Visitor;

import SyntaxTree.*;

public interface Visitor {

	Object visit(Node node);
	Object visit(OpNode node) throws Exception;
	Object visit(Leaf node) throws Exception;
	Object visit(ProgramOp node) throws Exception;
	Object visit(ProcDeclOp node) throws Exception;
	Object visit(UseNode node) throws Exception;
	Object visit(ScopeNode node) throws Exception;
	Object visit(MathOp node) throws Exception;
	Object visit(SingleOp node) throws Exception;
	Object visit(ConditionalOp node) throws Exception;
	Object visit(IfThenOp node) throws Exception;
	Object visit(IfThenElseOp node) throws Exception;
	Object visit(WhileOp node) throws Exception;
	Object visit(ForOp node) throws Exception;
	Object visit(CallOp node) throws Exception;
	Object visit(AssignOp node) throws Exception;
	Object visit(WriteOp node) throws Exception;
	Object visit(ReadOp node) throws Exception;
	Object visit(VarDeclOp node) throws Exception;
	Object visit(ParOp node) throws Exception;
	Object visit(VarInitOp node) throws Exception;
	Object visit(IncrementsOp node) throws Exception;
	Object visit(ModOp node) throws Exception;
	Object visit(DecreaseOp node) throws Exception;
	Object visit(SwitchOp node) throws Exception;
	Object visit(CaseListOp node) throws Exception;
	Object visit(CaseOp node) throws Exception;
	Object visit(DefaultOp node) throws Exception;
	Object visit(ArrayOp node) throws Exception;
	Object visit(ArrayAssignOp node) throws Exception;
	Object visit(ArrayIndexOp node) throws Exception;
	Object visit(StructOp node) throws Exception;
	Object visit(StructInitOp node) throws Exception;
	Object visit(InternalDefOp node) throws Exception;
}