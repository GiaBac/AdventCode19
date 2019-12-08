import java.util.ArrayList;
import java.util.List;

public class TestMainDay7 {

	public static void main(String[] args) {
		String intCodeInput = "3,8,1001,8,10,8,105,1,0,0,21,42,67,88,105,114,195,276,357,438,99999,3,9,101,4,9,9,102,3,9,9,1001,9,2,9,102,4,9,9,4,9,99,3,9,1001,9,4,9,102,4,9,9,101,2,9,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,1001,9,4,9,1002,9,4,9,101,2,9,9,1002,9,2,9,4,9,99,3,9,101,4,9,9,102,3,9,9,1001,9,5,9,4,9,99,3,9,102,5,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,99";
		String sampleCodeInput = "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0";
		String[] array = sampleCodeInput.split(",");

		List<String> phases = new ArrayList<>();
		phases.add("0");
		phases.add("1");
		phases.add("2");
		phases.add("3");
		phases.add("4");

		List<List<String>> allList = generateAllSequence(phases);

		System.out.println(allList.toString());
		System.out.println(allList.size());

		int maxTS = Integer.MIN_VALUE;
		List<String> seqMax = null;
		for (List<String> seq : allList) {
			Result res = new Result(0, false);
			Result resA = runIntCodeProgram(array.clone(), seq.get(0), Integer.toString(res.res));
			Result resB = runIntCodeProgram(array.clone(), seq.get(1), Integer.toString(resA.res));
			Result resC = runIntCodeProgram(array.clone(), seq.get(2), Integer.toString(resB.res));
			Result resD = runIntCodeProgram(array.clone(), seq.get(3), Integer.toString(resC.res));
			res = runIntCodeProgram(array.clone(), seq.get(4), Integer.toString(resD.res));

			if (res.res > maxTS) {
				maxTS = res.res;
				seqMax = seq;
			}
		}

		System.out.print("Max TS= " + maxTS + " From Seq=" + seqMax);

	}

	private static List<List<String>> generateAllSequence(List<String> phases) {
		List<List<String>> res = new ArrayList<List<String>>();

		if (phases.size() == 1) {
			res.add(phases);
			return res;
		}

		for (int i = 0; i < phases.size(); i++) {
			List<String> copy = new ArrayList<>(phases);
			String el = phases.get(i);
			copy.remove(i);
			List<List<String>> rest = generateAllSequence(copy);
			for (List<String> seq : rest) {
				seq.add(0, el);
				res.add(seq);
			}
		}

		return res;
	}

	private static Result runIntCodeProgram(String[] array, String phase, String input) {
		int idxNextElement = 0;
		int nextElem = Integer.parseInt(array[idxNextElement]);
		int mode3 = nextElem / 10000;
		int mode2 = (nextElem - (mode3 * 10000)) / 1000;
		int mode1 = (nextElem - (mode3 * 10000) - (mode2 * 1000)) / 100;
		int op = (nextElem - (mode3 * 10000) - (mode2 * 1000) - (mode1 * 100));

		int numInputInstr = 0;
		int count = 1;

		while (nextElem != 99) {
			switch (op) {
			case 99:
				throw new IllegalArgumentException("HALT");
			case 1:
				int el1 = retrieveValue(array, idxNextElement + 1, mode1);
				int el2 = retrieveValue(array, idxNextElement + 2, mode2);
				int el3 = Integer.parseInt(array[idxNextElement + 3]);
				Integer sum = el1 + el2;
				array[el3] = sum.toString();
				idxNextElement += 4;
				break;
			case 2:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				el2 = retrieveValue(array, idxNextElement + 2, mode2);
				el3 = Integer.parseInt(array[idxNextElement + 3]);
				Integer mul = el1 * el2;
				array[el3] = mul.toString();
				idxNextElement += 4;
				break;
			case 3:
				el1 = Integer.parseInt(array[idxNextElement + 1]);
				array[el1] = (numInputInstr == 0) ? phase : input;
				numInputInstr++;
				idxNextElement += 2;
				break;
			case 4:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);

				return new Result(el1, false);
			case 5:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				el2 = retrieveValue(array, idxNextElement + 2, mode2);
				if (el1 != 0) {
					idxNextElement = el2;
				} else {
					idxNextElement += 3;
				}
				break;
			case 6:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				el2 = retrieveValue(array, idxNextElement + 2, mode2);
				if (el1 == 0) {
					idxNextElement = el2;
				} else {
					idxNextElement += 3;
				}
				break;
			case 7:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				el2 = retrieveValue(array, idxNextElement + 2, mode2);
				el3 = Integer.parseInt(array[idxNextElement + 3]);
				if (el1 < el2) {
					array[el3] = "1";
				} else {
					array[el3] = "0";
				}
				idxNextElement += 4;
				break;
			case 8:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				el2 = retrieveValue(array, idxNextElement + 2, mode2);
				el3 = Integer.parseInt(array[idxNextElement + 3]);
				if (el1 == el2) {
					array[el3] = "1";
				} else {
					array[el3] = "0";
				}
				idxNextElement += 4;
				break;
			default:
				failure(array, idxNextElement, nextElem, count, "Something went wrong!");
			}
			count++;

			nextElem = Integer.parseInt(array[idxNextElement]);
			mode3 = nextElem / 10000;
			mode2 = (nextElem - (mode3 * 10000)) / 1000;
			mode1 = (nextElem - (mode3 * 10000) - (mode2 * 1000)) / 100;
			op = (nextElem - (mode3 * 10000) - (mode2 * 1000) - (mode1 * 100));
		}

		System.out.println("nextElem= " + nextElem);
		System.out.println("idxNextElement= " + idxNextElement);
		System.out.println("#iter= " + count);
		System.out.print("Array[idxNextElement]= " + array[idxNextElement]);

		throw new IllegalArgumentException();
	}

	private static void failure(String[] array, int idxNextElement, int nextElem, int count, String msg) {
		System.out.println(msg);
		System.out.println("A[idxNextElement+2]= " + array[idxNextElement + 2]);
		System.out.println("nextElem= " + nextElem);
		System.out.println("idxNextElement= " + idxNextElement);
		System.out.println("#iter= " + count);
		System.exit(-1);
	}

	private static int retrieveValue(String[] array, int idx, int mode) {
		assert (mode == 0 || mode == 1);

		int val = Integer.parseInt(array[idx]);

		return (mode == 1) ? val : Integer.parseInt(array[val]);
	}
}

class Result {
	int res;
	boolean isStop;

	public Result(int res, boolean isStop) {
		this.res = res;
		this.isStop = isStop;
	}
}
