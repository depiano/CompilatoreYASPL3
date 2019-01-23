package FunctionUtility;
import FunctionUtility.Paramater;
import java.util.ArrayList;

public class FunctionArgs {
	
	private ArrayList<Paramater> inParameters;
	private ArrayList<Paramater> outParameters;
	private ArrayList<Paramater> inoutParameters;

	public FunctionArgs() {
		this.inParameters= new ArrayList<Paramater>();
		this.outParameters= new ArrayList<Paramater>();
		this.inoutParameters = new ArrayList<Paramater>();
	}
	
	public void addInputParameters(Paramater paramIn) {
		this.inParameters.add(paramIn);
	}
	
	public void addOutputParameters(Paramater paramOut) {
		this.outParameters.add(paramOut);
	}
	public void addInOutParameters(Paramater paramInOut) {
		this.inoutParameters.add( paramInOut);
	}

	public ArrayList<Paramater> getInputParameters() {
		return inParameters;
	}

	
	
	public void setInputParameters(ArrayList<Paramater> inputParameters) {
		this.inParameters = inputParameters;
	}

	public ArrayList<Paramater> getOutputParameters() {
		return outParameters;
	}

	public void setOutputParameters(ArrayList<Paramater> outputParameters) {
		this.outParameters = outputParameters;
	}
	
	public ArrayList<Paramater> getInOutParameters() {
		return inoutParameters;
	}
	
	public void setInOutParameters(ArrayList<Paramater> inOutParameters) {
		this.inoutParameters = inOutParameters;
	}
	
	
	public String toString() {
		return "(In: "+inParameters.toString()+", Out: "+outParameters.toString()+", InOut: "+inoutParameters.toString()+")";
	}
	
}
