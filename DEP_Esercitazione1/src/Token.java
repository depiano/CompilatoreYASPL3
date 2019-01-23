/**
 * 
 * @author Antonio De Piano
 * eMail: depianoantonio@gmail.com
 * wEb site: http://www.depiano.it
 * 
 */

public class Token {
	String name;
	String attributeValue;

	public Token(String name) {
		this.name=name;
	}
	public Token(String name, String attributeValue) {
		this.name=name;
		this.attributeValue=attributeValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	@Override
	public String toString() {
		if(attributeValue==null)
			return "Token <"+ name + ">";
		return "Token <"+name+","+attributeValue + ">";
	}
}
