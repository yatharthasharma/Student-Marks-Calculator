package uk.ac.newcastle;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class MarkCalculator {
	private static final int numberOfModules = 6;
	private static final int minimumComputedMark = 35;
	private static final int courseworkWeighting = 50;

	/**
	 * @param examMark       Marks secured in the exam
	 * @param courseworkMark Marks secured in the coursework
	 *
	 * @return Computed marks for the module for which the exam and coursework marks were provided
	 */
	private int computedMark(int examMark, int courseworkMark) {
		return ((courseworkMark * courseworkWeighting) + (examMark * (100 - courseworkMark))) / 100;
	}

	private int stageAverage(int[] computedMarks) {
		int totalMarks = 0;
		for (int moduleMark : computedMarks) {
			totalMarks += moduleMark;
		}
		return totalMarks / numberOfModules;
	}

	private HashMap<String, Integer> moduleResults(int[] computedMarks) {
		HashMap<String, Integer> moduleResults = new HashMap<>();
		for (int i = 0; i < numberOfModules; i += 1) {
			if (computedMarks[i] >= 40) {
				if (moduleResults.containsKey("Pass")) {
					moduleResults.put("Pass", moduleResults.get("Pass") + 1);
				} else {
					moduleResults.put("Pass", 1);
				}
			} else if (computedMarks[i] >= 35) {
				if (moduleResults.containsKey("Compensatable Fail")) {
					moduleResults.put("Compensatable Fail", moduleResults.get("Compensatable Fail") + 1);
				} else {
					moduleResults.put("Compensatable Fail", 1);
				}
			} else {
				if (moduleResults.containsKey("Fail")) {
					moduleResults.put("Fail", moduleResults.get("Fail") + 1);
				} else {
					moduleResults.put("Fail", 1);
				}
			}
		}
		return moduleResults;
	}

	private int[] computeMarks(int @NotNull [] examMarks, int[] courseworkMarks) throws Exception {
		int[] computedMarks = new int[numberOfModules];
		if (examMarks.length != numberOfModules || courseworkMarks.length != numberOfModules) {
			throw new Exception("Both set of marks not provided for at least one of the modules.");
		}
		for (int i = 0; i < numberOfModules; i += 1) {
			if (examMarks[i] < minimumComputedMark || courseworkMarks[i] < minimumComputedMark) {
				computedMarks[i] = 35;
			} else {
				computedMarks[i] = computedMark(examMarks[i], courseworkMarks[i]);
			}
		}
		return computedMarks;
	}

	private String stageResult(int averageMarks, int[] computedMarks) {
		final HashMap<String, Integer> moduleResults = moduleResults(computedMarks);
		if (moduleResults.get("Pass") == numberOfModules) {
			return "Pass";
		} else if (averageMarks >= 40 && moduleResults.get("Fail") == null
						&& (moduleResults.get("Compensatable Fail") == 1
						|| moduleResults.get("Compensatable Fail") == 2)) {
			return "Pass by Compensation";
		} else {
			return "Fail";
		}
	}

	public String computeResult(int @NotNull [] examMarks, int[] courseworkMarks) throws Exception {
		if (examMarks.length != numberOfModules || courseworkMarks.length != numberOfModules) {
			throw new Exception("Both set of marks not provided for at least one of the modules.");
		} else {
			int[] computedMarks = computeMarks(examMarks, courseworkMarks);
			int averageMarks = stageAverage(computedMarks);
			return stageResult(averageMarks, computedMarks);
		}
	}
}
