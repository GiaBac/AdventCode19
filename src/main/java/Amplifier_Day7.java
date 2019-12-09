
public class Amplifier_Day7 {

	private int idxNextElement;
	private String[] array;

	public Amplifier_Day7(String[] array) {
		this.idxNextElement = 0;
		this.array = array;
	}

	public Result runIntCodeProgram(String phase, String input, boolean usePhase) {
		int nextElem = Integer.parseInt(array[idxNextElement]);
		int mode3 = nextElem / 10000;
		int mode2 = (nextElem - (mode3 * 10000)) / 1000;
		int mode1 = (nextElem - (mode3 * 10000) - (mode2 * 1000)) / 100;
		int op = (nextElem - (mode3 * 10000) - (mode2 * 1000) - (mode1 * 100));

		int numInputInstr = 0;
		int count = 1;

		while (numInputInstr < array.length) {
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
				array[el1] = (numInputInstr == 0 && usePhase) ? phase : input;
				numInputInstr++;
				idxNextElement += 2;
				break;
			case 4:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				idxNextElement += 2;

				return new Result(el1, false, idxNextElement + 2);
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

	private void failure(String[] array, int idxNextElement, int nextElem, int count, String msg) {
		System.out.println(msg);
		System.out.println("A[idxNextElement+2]= " + array[idxNextElement + 2]);
		System.out.println("nextElem= " + nextElem);
		System.out.println("idxNextElement= " + idxNextElement);
		System.out.println("#iter= " + count);
		System.exit(-1);
	}

	private int retrieveValue(String[] array, int idx, int mode) {
		assert (mode == 0 || mode == 1);

		int val = Integer.parseInt(array[idx]);

		return (mode == 1) ? val : Integer.parseInt(array[val]);
	}
}
