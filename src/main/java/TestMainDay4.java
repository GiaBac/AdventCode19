
public class TestMainDay4 {

	public static void main(String[] args) {
		int min = 256310;
		int max = 732736;
		int tot = 0;

		System.out.println("Test(111111): " + testPwd(111111));
		System.out.println("Test(223450): " + testPwd(223450));
		System.out.println("Test(123789): " + testPwd(123789));
		System.out.println("Test(112233): " + testPwd(112233));
		System.out.println("Test(123444): " + testPwd(123444));
		System.out.println("Test(111122): " + testPwd(111122));

		int current = min;
		while (current <= max) {
			if (testPwd(current)) {
				System.out.println(current);
				tot++;
			}
			current++;
		}

		System.out.println("Total: " + tot);
	}

	private static boolean testPwd(int current) {
		return sameDigit(current) && neverDecrease(current) && !largeGrpOnly(current);
	}

	private static boolean largeGrpOnly(int current) {
		String str = Integer.toString(current);
		char[] array = str.toCharArray();
		char prevC = ' ';
		int minLargeGrp = 1000;
		int currLargeGrpSize = 1;
		for (char c : array) {
			if (c == prevC)
				currLargeGrpSize++;
			else {
				if (currLargeGrpSize != 1 && currLargeGrpSize < minLargeGrp)
					minLargeGrp = currLargeGrpSize;
				currLargeGrpSize = 1;
			}
			prevC = c;
		}

		if (currLargeGrpSize != 1 && currLargeGrpSize < minLargeGrp)
			minLargeGrp = currLargeGrpSize;

		return minLargeGrp > 2;
	}

	private static boolean neverDecrease(int current) {
		String str = Integer.toString(current);
		char[] array = str.toCharArray();
		char prevC = array[0];
		for (char c : array) {
			if (c < prevC)
				return false;
			prevC = c;
		}
		return true;
	}

	private static boolean sameDigit(int current) {
		String str = Integer.toString(current);
		char[] array = str.toCharArray();
		char prevC = ' ';
		for (char c : array) {
			if (c == prevC)
				return true;
			prevC = c;
		}

		return false;
	}

}
