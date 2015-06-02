/*********************************
 * Author: Xiaodong Zhou
 * Date: 2015/04/05
 *********************************/
package CV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ProcessInput.ProcessInputData;
import TreeDefination.TreeNode;
import C45CoreAlgorithm.ConstructTree;
import DataDefination.Attribute;
import DataDefination.Instance;

public class CrossValidation {
	private ArrayList<Attribute> attributes;
	private ArrayList<Instance> trainInstances;
	private ArrayList<Instance> testInstances;
	private ArrayList<ArrayList<Instance>> testBundles;
	private Attribute target;
	private TreeNode root;
	private ArrayList<Instance> result;
	private ArrayList<Instance> totalInstances;
	private ArrayList<Double> scores;
	Random rand;
	
	/**
	 * Constructor
	 * @param trainData
	 * @throws IOException
	 */
	public CrossValidation(String trainData) throws IOException {
		result = new ArrayList<Instance>();
		
		ProcessInputData input = new ProcessInputData(trainData);
		this.attributes = input.getAttributeSet();
		target = input.getTargetAttribute();
		this.testBundles = new ArrayList<ArrayList<Instance>>();
		this.totalInstances = input.getInstanceSet();
		rand = new Random(totalInstances.size());
	}
	
	/**
	 * Shuffle data and put them into k bundles, preparing for cross validation on k folds.
	 * @param k
	 */
	public void shuffle(int k) {
		int total_size = totalInstances.size();
		int average = total_size / k;
		
		for(int i = 0; i < k - 1; i++) {
			ArrayList<Instance> curBundle = new ArrayList<Instance>();
			for(int j = 0; j < average; j++) {
				int size = totalInstances.size();
				int curIndex = rand.nextInt(size);
				curBundle.add(totalInstances.get(curIndex));
				totalInstances.remove(curIndex);
			}
			testBundles.add(curBundle);
		}
		
		ArrayList<Instance> lastBundle = new ArrayList<Instance>();
		for(int i = 0; i < totalInstances.size(); i++) {
			lastBundle.add(totalInstances.get(i));
		}
		
		testBundles.add(lastBundle);
	}
	
	/**
	 * Mine input data (e.g. put target attribute label on input data).
	 */
	private void mine() {
		for (int i = 0; i < testInstances.size(); i++) {
			TreeNode node = root;
			Instance currInstance = testInstances.get(i);
			Instance resInstance = result.get(i);
			while (!node.getType().equals("leaf")) {
				String attributeName = node.getAttribute().getName();
				String attributeType = node.getAttribute().getType();
				HashMap<String, String> attributeValuePairs = currInstance.getAttributeValuePairs();
				String value = attributeValuePairs.get(attributeName);
				if (attributeType.equals("continuous")) {
					HashMap<String, TreeNode> children = node.getChildren();
					String tmp = "";
					for (String s : children.keySet()) {
						String threshold = s.substring(4);
						if (Double.parseDouble(value) < Double.parseDouble(threshold)) {
							tmp = "less";
						} else {
							tmp = "more";
						}
						String curLabel = s.substring(0, 4);
						if (tmp.equals(curLabel)) node = children.get(s);
					}
				} else {
					HashMap<String, TreeNode> children = node.getChildren();
					node = children.get(value);
				}
			}
			HashMap<String, String> pairs = resInstance.getAttributeValuePairs();
			pairs.put("Test" + target.getName(), node.getTargetLabel());
		}
	}
	
	/**
	 * Get result of mined data.
	 * @return the result of mined data.
	 */
	public ArrayList<Instance> getResult() {
		mine();
		return result;
	}
	
	/**
	 * Do cross validation on input data.
	 * @param crossValidationN
	 * @return the result of cross validation
	 * @throws IOException
	 */
	public ArrayList<Double> validate(int crossValidationN) throws IOException {
		shuffle(crossValidationN);
		scores = new ArrayList<Double>();
		for(int i = 0; i < testBundles.size(); i++) {
			trainInstances = new ArrayList<Instance>();
			testInstances = new ArrayList<Instance>();
			result = new ArrayList<Instance>();
			for(int j = 0; j < testBundles.size(); j++) {
				if(i == j) {
					result.addAll(testBundles.get(j));
					testInstances.addAll(testBundles.get(j));
				} else {
					trainInstances.addAll(testBundles.get(j));
				}
			}
			ConstructTree tree = new ConstructTree(trainInstances, attributes, target);
			root = tree.construct();
			
			int correct = 0;
			ArrayList<Instance> res = getResult();
			for (Instance item : res) {				
				String testLabel = item.getAttributeValuePairs().get("Test" + target.getName());
				String label = item.getAttributeValuePairs().get(target.getName());
				if(testLabel.equals(label)) {
					correct++;
				}
			}
			scores.add(correct * 1.0 / res.size());
		}
		return scores;
	}
	/**
	 * 
	 * @return tree root
	 */
	public TreeNode getRoot() {
		return root;
	}
}