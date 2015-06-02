/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/01
 *********************************/
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import DataDefination.Attribute;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;

public class ChooseAttribute {
	
	private Attribute chosen;
	private HashMap<String, ArrayList<Instance>> subset;
	private double infoGain;
	private double threshold;
	
	/**
	 * Constructor: initialize fields
	 * @param target
	 * @param attributes
	 * @param instances
	 * @throws IOException
	 */
	public ChooseAttribute(Attribute target, ArrayList<Attribute> attributes, 
			ArrayList<Instance> instances) throws IOException {
		
		// Initialize variables
		chosen = null;
		infoGain = -1;
		subset = null;
		
		// Iterate to find the attribute with the largest information gain
		for (Attribute currAttribute : attributes) {
			double currInfoGain = 0;
			HashMap<String, ArrayList<Instance>> currSubset = null;
			
			if (currAttribute.getType().equals("continuous")) {
				InfoGainContinuous contigous = new InfoGainContinuous(currAttribute, target, instances);
				currInfoGain = contigous.getInfoGain();
				currSubset = contigous.getSubset();
				threshold = contigous.getThreshold();
			} else {
				InfoGainDiscrete discrete = new InfoGainDiscrete(target, currAttribute, instances);
				currInfoGain = discrete.getInfoGain();
				currSubset = discrete.getSubset();
			}
			if (currInfoGain > infoGain) {
				infoGain = currInfoGain;
				chosen = currAttribute;
				subset = currSubset;
			}
		}
	}
	
	public Attribute getChosen() {
		return chosen;
	}
	
	public double getInfoGain() {
		return infoGain;
	}
	
	public HashMap<String, ArrayList<Instance>> getSubset() {
		return subset;
	}
	
	public double getThreshold() {
		return threshold;
	}
	
	public String toString() {
		return "Chosen attribute: " + chosen + "\n" + "InfoGain: " + infoGain + "\n"
				+ "Subset: " + subset;
	}
}