package FunctionUtility;

import java.util.ArrayList;
import java.util.Hashtable;

/*
*Classe usata per salvare i tipi in input e in output di ogni funzione (in,inout,out);
*/

public class FunctionTable extends Hashtable<String, FunctionArgs>{
	
	private String nomeFunzione;
	
	public FunctionTable(String nomeFunzione) {
		super();
		this.nomeFunzione= nomeFunzione;
	}
	
	public void addFunzione(String nomeF) {
		this.put(nomeF, new FunctionArgs());
	}
	
	public void addParamInFunction(String nomeFunzioneTabella, Paramater paramIn) {
		if(this.containsKey(nomeFunzioneTabella)) {
			this.get(nomeFunzioneTabella).addInputParameters(paramIn);
		}else {
			this.addFunzione(nomeFunzioneTabella);
			this.get(nomeFunzioneTabella).addInputParameters(paramIn);
		}
	}
	
	public void addParamOutFunction(String nomeFunzioneTabella, Paramater paramOut) {
			if(this.containsKey(nomeFunzioneTabella)) {
			this.get(nomeFunzioneTabella).addOutputParameters(paramOut);
		}else {
			this.addFunzione(nomeFunzioneTabella);
			this.get(nomeFunzioneTabella).addOutputParameters(paramOut);
		}
	}
	
	
	public void addParamInOutFunction(String nomeFunzioneTabella, Paramater paramInOut) {
		if(this.containsKey(nomeFunzioneTabella)) {
		this.get(nomeFunzioneTabella).addInOutParameters(paramInOut);
	}else {
		this.addFunzione(nomeFunzioneTabella);
		this.get(nomeFunzioneTabella).addInOutParameters(paramInOut);
	}
}
	
	public ArrayList<Paramater> getParamIn(String nomeF)throws Exception{
			try{
				return this.get(nomeF).getInputParameters();
			}catch(Exception exc) {
				throw new Exception("Illegal Number Of Arguments In Function " + nomeF.toUpperCase());

			}
	}

	public ArrayList<Paramater> getParamOut(String nomeF)throws Exception{
		try {
			return this.get(nomeF).getOutputParameters();
		}catch(Exception exc) {

			throw new Exception("Illegal Number Of Arguments In Function " + nomeF.toUpperCase());

		}
	}

	public ArrayList<Paramater> getParamInOut(String nomeF)throws Exception{
		try {

			return this.get(nomeF).getInOutParameters();
		}catch(Exception exc) {

			throw new Exception("Illegal Number Of Arguments In Function " + nomeF.toUpperCase());

		}
	}

	
	public String getNomeFunzione() {
		return nomeFunzione;
	}

	public void setNomeFunzione(String nomeFunzione) {
		this.nomeFunzione = nomeFunzione;
	}

	public String toString() {
		return "[Tabella Funzioni]" +super.toString().toUpperCase();
	}
	

}
