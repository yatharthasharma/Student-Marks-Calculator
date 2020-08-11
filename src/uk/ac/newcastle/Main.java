package uk.ac.newcastle;

public class Main {
	public static void main(String[] args) {
		try {
			MarkCalculator markCalculator = new MarkCalculator();
			String result = markCalculator.computeResult(new int[]{36, 32, 33, 67, 93, 42},
							new int[]{52, 56, 67, 87, 98, 100});
			System.out.println(result);
		} catch (Exception error) {
			System.out.print(error);
			System.err.println("Internal server error.");
		}
	}
}
