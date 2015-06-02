/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/01
 *********************************/
package ProcessOutput;

import java.util.ArrayList;
import java.util.HashMap;
import TreeDefination.TreeNode;

public class PrintTree {
	private String pic;
	
	/**
	 * This method print out each path of the tree from root to leaf.
	 * @param root
	 * @return ArrayList<String>
	 */
	public ArrayList<String> printDFS(TreeNode root) {
		ArrayList<String> res = new ArrayList<String>();
		printDFS(root, new StringBuilder(), res);
		return res;
	}	
	private void printDFS(TreeNode root, StringBuilder sb, ArrayList<String> res) {
		if (root.getType().equals("leaf")) {
			StringBuilder curr = new StringBuilder(sb);
			curr.append(root.getTargetLabel());
			curr.append("\n");
			res.add(curr.toString());
		} else {
			sb.append(root.getAttribute().getName());
			HashMap<String, TreeNode> children = root.getChildren();
			for (String valueName : children.keySet()) {
				StringBuilder curr = new StringBuilder(sb);
				curr.append("(");
				curr.append(valueName);
				curr.append(")");
				printDFS(children.get(valueName), curr, res);
			}
		}	
	}
	
	@Override
	public String toString() {
		return pic;
	}
}