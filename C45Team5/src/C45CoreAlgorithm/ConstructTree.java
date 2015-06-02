/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/01
 *********************************/
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ProcessInput.ProcessInputData;
import TreeDefination.TreeNode;
import DataDefination.Instance;
import DataDefination.Attribute;

public class ConstructTree {
	private ArrayList<Attribute> attributes;
	private ArrayList<Instance> instances;
	private Attribute target;
	
	public ConstructTree(String fileName) throws IOException {
		ProcessInputData input = new ProcessInputData(fileName);
		attributes = input.getAttributeSet();
		instances = input.getInstanceSet();
		target = input.getTargetAttribute();
	}
	
	public ConstructTree(ArrayList<Instance> instances, ArrayList<Attribute> attributes,
			Attribute target) {
		this.instances = instances;
		this.attributes = attributes;
		this.target = target;
	}
	
	/**
	 * Construct tree
	 * @return TreeNode
	 * @throws IOException
	 */
	public TreeNode construct() throws IOException {
		return constructTree(target, attributes, instances);
	}
	
	/**
	 * Construct tree recursively. First make the root node, then construct its subtrees 
	 * recursively, and finally connect root with subtrees.
	 * @param target
	 * @param attributes
	 * @param instances
	 * @return TreeNode
	 * @throws IOException
	 */
	private TreeNode constructTree(Attribute target, ArrayList<Attribute> attributes, 
			ArrayList<Instance> instances) throws IOException {
		
		/*
		 *  Stop when (1) entropy is zero
		 *  (2) no attribute left
		 */
		if (Entropy.calculate(target, instances) == 0 || attributes.size() == 0) {
			String leafLabel = "";
			if (Entropy.calculate(target, instances) == 0) {
				leafLabel = instances.get(0).getAttributeValuePairs().get(target.getName());
			} else {
				leafLabel = getMajorityLabel(target, instances);
			}
			TreeNode leaf = new TreeNode(leafLabel);
			return leaf;
		}
		
		// Choose the root attribute
		ChooseAttribute choose = new ChooseAttribute(target, attributes, instances);
		Attribute rootAttr = choose.getChosen();
		
		// Remove the chosen attribute from attribute set
		attributes.remove(rootAttr);
		
		// Make a new root
		TreeNode root = new TreeNode(rootAttr);
		
		// Get value subsets of the root attribute to construct branches
		HashMap<String, ArrayList<Instance>> valueSubsets = choose.getSubset();
		for (String valueName : valueSubsets.keySet()) {
			ArrayList<Instance> subset = valueSubsets.get(valueName);
			if (subset.size() == 0) {
				String leafLabel = getMajorityLabel(target, instances);
				TreeNode leaf = new TreeNode(leafLabel);
				root.addChild(valueName, leaf);
			} else {
				TreeNode child = constructTree(target, attributes, subset);
				root.addChild(valueName, child);
			}			
		}		
		
		// Remember to add it again!
		attributes.add(rootAttr);
		
		return root;
	}
	
	/**
	 * Get the majority target class label from instances
	 * @param target
	 * @param instances
	 * @return String
	 * @throws IOException
	 */
	public String getMajorityLabel(Attribute target, ArrayList<Instance> instances) throws IOException {
		ArrayList<String> valuesOfTarget = target.getValues();
		String targetName = target.getName();
		HashMap<String, Integer> countValueOfTarget = new HashMap<String, Integer>();
		for (String s : valuesOfTarget) {
			countValueOfTarget.put(s, 0);
		}
		for (Instance instance : instances) {
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtTarget = attributeValuePairsOfInstance.get(targetName);
			if (!countValueOfTarget.containsKey(valueOfInstanceAtTarget)) 
				throw new IOException("Invalid input data");
			countValueOfTarget.put(valueOfInstanceAtTarget, 
					countValueOfTarget.get(valueOfInstanceAtTarget) + 1);
		}
		String maxLabel = "";
		int maxCount = 0;
		for (String s : countValueOfTarget.keySet()) {
			int currCount = countValueOfTarget.get(s);
			if (currCount > maxCount) {
				maxCount = currCount;
				maxLabel = s;
			}
		}
		return maxLabel;
	}
}