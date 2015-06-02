/*
 * Author: Charlotte Lin
 * Date: 2015/3/31
 * 
 */
package DataDefination;

import java.io.IOException;
import java.util.ArrayList;

public class Attribute {
	
	// Field name: name of the attribute
	private String name;
	
	// Field type: discrete value or continuous value 
	private String type;
	
	// Field values: values. If discrete, stores values; if continuous, stores "real"
	private ArrayList<String> values;
	
	public Attribute(String name, String values) throws IOException {
		
		// Initialize name
		this.name = name;
		
		// Initialize pattern
		if (values.equals("real")) {
			type = "continuous";
		} else {
			type = "discrete";
		}
		
		// Initialize values		
		if (values.equals("real")) {
			this.values = new ArrayList<String>();
			this.values.add("real");
		} else {
			this.values = constructValues(values);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public ArrayList<String> getValues() {
		return values;
	}
	
	public String toString() {
		return "@Attribute Name: " + name + "; @Attribute Type: " + type +
				 "; @Attribute Values: " + values;
	}
	
	private ArrayList<String> constructValues(String s) throws IOException {
		if (s == null || s.length() < 2) throw new IOException("Invalid input format");
		s = s.substring(1, s.length() - 1);
		String[] sArr = s.split(",");
		ArrayList<String> values = new ArrayList<String>();
		for (String item : sArr) {
			values.add(item);
		}
		return values;
	}
	
	// unit test
	public static void main(String[] args) throws IOException {
		String s1 = "Service_type";
		String s2 = "{Fund,Loan,CD,Bank_Account,Mortgage}";
		Attribute test = new Attribute(s1, s2);
		System.out.println(test);
	}
}