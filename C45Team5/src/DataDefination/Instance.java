/*
 * Author: Charlotte Lin
 * Date: 2015/3/31
 * 
 */
package DataDefination;

import java.util.HashMap;

public class Instance {
	private static int count = 0;
	private int index;
	private HashMap<String, String> attributeValuePairs;
	public Instance() {
		index = count;
		attributeValuePairs = new HashMap<String, String>();
		count++;
	}
	public void addAttribute(String name, String value) {
		attributeValuePairs.put(name, value);
	}
	public int getInstanceIndex() {
		return index;
	}
	public HashMap<String, String> getAttributeValuePairs() {
		return attributeValuePairs;
	}
	public String toString() {
		return "@Instance Index: " + index + "; " 
				+ "@Instance Attribute Value Pairs: " + attributeValuePairs;
	}
	
	public static void main(String[] args) {
		Instance test = new Instance();
		test.addAttribute("Service_type", "Fund");
		test.addAttribute("Servi", "Fund");
		System.out.println(test);
		Instance test2 = new Instance();
		test2.addAttribute("Service_type", "Fund");
		test2.addAttribute("Servi", "Fund");
		System.out.println(test2);
	}
}