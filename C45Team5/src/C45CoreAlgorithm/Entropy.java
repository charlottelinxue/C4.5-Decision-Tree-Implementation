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

public class Entropy {
	
	/**
	 * Calculate entropy of instances for the target attribute.
	 * Only for discrete attribute.
	 * @param target
	 * @param instances
	 * @return double
	 * @throws IOException
	 */
	public static double calculate(Attribute target, ArrayList<Instance> instances) throws IOException{
		ArrayList<String> valuesOfTarget = target.getValues();
		String targetName = target.getName();
		HashMap<String, Integer> countValueOfTarget = new HashMap<String, Integer>();
		for (String s : valuesOfTarget) {
			countValueOfTarget.put(s, 0);
		}
		for (Instance instance : instances) {
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtTarget = attributeValuePairsOfInstance.get(targetName);
			if (!countValueOfTarget.containsKey(valueOfInstanceAtTarget)) {
				throw new IOException("Invalid input data");
			}
			countValueOfTarget.put(valueOfInstanceAtTarget, 
					countValueOfTarget.get(valueOfInstanceAtTarget) + 1);
		}
		int totalN = instances.size();
		double entropy = 0;
		
		for (String s : valuesOfTarget) {
			int countSingleValue = countValueOfTarget.get(s);
			if (countSingleValue == 0) continue;
			if (countSingleValue == totalN) return 0;
			double pValue = ((double) countSingleValue) / ((double)totalN);
			double itemRes = -pValue * (Math.log(pValue));
			entropy += itemRes;
		}
		return entropy;
	}
	
	/**
	 * Calculate entropy of instances for the target attribute.
	 * Only for continuous attribute.
	 * Reason: arguments of methods are different. The arguments of this method has start and 
	 * end. Such arguments can reuse instances without separating them into different arrayLists,
	 * saving time and space.
	 * @param target
	 * @param instances
	 * @param start
	 * @param end
	 * @return double
	 * @throws IOException
	 */
	public static double calculateConti(Attribute target, ArrayList<Instance> instances, 
			int start, int end) throws IOException {
		ArrayList<String> valuesOfTarget = target.getValues();
		String targetName = target.getName();
		HashMap<String, Integer> countValueOfTarget = new HashMap<String, Integer>();
		for (String s : valuesOfTarget) {
			countValueOfTarget.put(s, 0);
		}
		for (int i = start; i <= end; i++) {
			Instance instance = instances.get(i);
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtTarget = attributeValuePairsOfInstance.get(targetName);
			if (!countValueOfTarget.containsKey(valueOfInstanceAtTarget)) 
				throw new IOException("Invalid input data");
			countValueOfTarget.put(valueOfInstanceAtTarget, 
					countValueOfTarget.get(valueOfInstanceAtTarget) + 1);
		}
		int totalN = instances.size();
		double entropy = 0;
		
		for (String s : valuesOfTarget) {
			int countSingleValue = countValueOfTarget.get(s);
			if (countSingleValue == 0) continue;
			if (countSingleValue == totalN) return 0;
			double pValue = ((double) countSingleValue) / ((double)totalN);
			double itemRes = -pValue * (Math.log(pValue));
			entropy += itemRes;
		}
		return entropy;
	}
}