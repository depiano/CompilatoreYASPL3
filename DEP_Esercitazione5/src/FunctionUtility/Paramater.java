/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * web site: http://www.depiano.it
 *
 */

package FunctionUtility;

public class Paramater {
	
	private String parameterType;
	private int parameterPosition;
	
	public Paramater(String parameterType, int parameterPosition) {
		this.parameterType = parameterType;
		this.parameterPosition = parameterPosition;
	}
	
	public Paramater() {
	}

	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public int getPositionType() {
		return parameterPosition;
	}
	public void setPositionType(int positionType) {
		this.parameterPosition = positionType;
	}
	@Override
	public String toString() {
		return "[Type=" + parameterType + ", Position=" + parameterPosition + "]";
	}
	
	
	
}
