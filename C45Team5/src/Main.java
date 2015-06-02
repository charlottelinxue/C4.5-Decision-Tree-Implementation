/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/06
 *********************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import CV.CrossValidation;
import CV.CrossValidationWithPruning;
import ProcessOutput.PrintTree;

public class Main {
	public static void main(String[] args) throws IOException {
				
		Scanner in = new Scanner(System.in);
		System.out.println("===============================================================");
		CrossValidation cv = new CrossValidation(args[0]);
		CrossValidationWithPruning cvP = new CrossValidationWithPruning(args[0]);
		
		ArrayList<Double> final_score = cv.validate(10);
		ArrayList<Double> final_score_P = cvP.validate(10);
		
		double r = 0;
		double rP = 0;
		for(int i = 0; i < final_score.size(); i++) {
			r += final_score.get(i);
			rP += final_score_P.get(i);
		}
		PrintTree print = new PrintTree();
		
		System.out.println("**********************");
		System.out.println("Cross Validation Accuracy before Pruning: " + r / 10);
		System.out.println("Cross Validation Accuracy after Pruning: " + rP / 10);	
		System.out.println("**********************");
		System.out.println("Do you want to see the tree before pruning? (y/n)");
		String printTreeBefore = in.nextLine();
		if (printTreeBefore.equals("y")) {
			System.out.println("Tree before Pruning: \n" + print.printDFS(cv.getRoot()));
		}
		System.out.println("**********************");
		System.out.println("Do you want to see the tree after pruning? (y/n)");
		String printTreeAfter = in.nextLine();
		if (printTreeAfter.equals("y")) {
			System.out.println("Tree after Pruning: \n" + print.printDFS(cvP.getRoot()));
		}
		System.out.println("**********************");
		System.out.println("(: The end. Thank you! :)");
		System.out.println("=======================@CMU Team Supernova@====================");
		in.close();
	}
}