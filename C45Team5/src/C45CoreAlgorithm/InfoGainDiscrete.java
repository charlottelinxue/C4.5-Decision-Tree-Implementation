/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/01
 *********************************/
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import DataDefination.Attribute;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;

public class InfoGainDiscrete {
	
	private Attribute attribute;
	private double infoGain;
	private HashMap<String, ArrayList<Instance>> subset;
	
	/**
	 * Constructor: initialize fields. This class is for calculating the information gain for
	 * discrete attribute.
	 * @param target
	 * @param attribute
	 * @param instances
	 * @throws IOException
	 */
	public InfoGainDiscrete(Attribute target, Attribute attribute, ArrayList<Instance> instances) 
			throws IOException {
		
		this.attribute = attribute;
		
		ArrayList<String> valuesOfAttribute = attribute.getValues();
		
		String attributeName = attribute.getName();
		subset = new HashMap<String, ArrayList<Instance>>();
		for (String s : valuesOfAttribute) {
			subset.put(s, new ArrayList<Instance>());
		}
		for (Instance instance : instances) {
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtAttribute = attributeValuePairsOfInstance.get(attributeName);
			if (!subset.containsKey(valueOfInstanceAtAttribute)) 
				throw new IOException("Invalid input data");
			subset.get(valueOfInstanceAtAttribute).add(instance);
		}
		
		int totalN = instances.size();
		infoGain = Entropy.calculate(target, instances);
		
		
		for (String s : subset.keySet()) {
			ArrayList<Instance> currSubset = subset.get(s);
			int subN = currSubset.size();
			double subRes = ((double) subN) / ((double) totalN) * 
					Entropy.calculate(target, currSubset);
			infoGain -= subRes;
		}
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public double getInfoGain() {
		return infoGain;
	}
	
	public HashMap<String, ArrayList<Instance>> getSubset() {
		return subset;
	}
	
	public String toString() {
		return "Attribute: " + attribute + "\n"  
				+ "InfoGain: " + infoGain + "\n" + "Subset: " + subset;
	}
}