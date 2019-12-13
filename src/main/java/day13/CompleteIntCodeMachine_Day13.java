package day13;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompleteIntCodeMachine_Day13 {

	private double idxNextElement;
	private int relativeIdxBase;
	private String[] array;
	private Map<Double, String> moreMemory;
	InputProvider inputProvider;

	public CompleteIntCodeMachine_Day13(String[] array, InputProvider inputProvider) {
		this.inputProvider = inputProvider;
		this.idxNextElement = 0;
		relativeIdxBase = 0;
		moreMemory = new HashMap<>();
		this.array = array;
	}

	public Result11 runIntCodeProgram() throws IOException {
		double nextElem = readArrayValue(array, idxNextElement);
		int mode3 = new Double(nextElem / 10000).intValue();
		int mode2 = new Double((nextElem - (mode3 * 10000)) / 1000).intValue();
		int mode1 = new Double((nextElem - (mode3 * 10000) - (mode2 * 1000)) / 100).intValue();
		int op = new Double((nextElem - (mode3 * 10000) - (mode2 * 1000) - (mode1 * 100))).intValue();

		int count = 1;

		while (op != 99) {
			switch (op) {
			case 1:
				double el1 = retrieveValue(array, idxNextElement + 1, mode1);
				double el2 = retrieveValue(array, idxNextElement + 2, mode2);
				double el3 = readArrayValue(array, idxNextElement + 3);
				Double sum = el1 + el2;
				writeValue(array, el3, sum.toString(), mode3 == 2);
				idxNextElement += 4;
				break;
			case 2:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				el2 = retrieveValue(array, idxNextElement + 2, mode2);
				el3 = readArrayValue(array, idxNextElement + 3);
				Double mul = el1 * el2;
				writeValue(array, el3, mul.toString(), mode3 == 2);
				idxNextElement += 4;
				break;
			case 3:
				el1 = readArrayValue(array, idxNextElement + 1);
				writeValue(array, el1, inputProvider.provideInput(), mode1 == 2);
				idxNextElement += 2;
				break;
			case 4:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				idxNextElement += 2;

				return new Result11(el1, false, idxNextElement + 2);
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
				el3 = readArrayValue(array, idxNextElement + 3);
				if (el1 < el2) {
					writeValue(array, el3, "1", mode3 == 2);
				} else {
					writeValue(array, el3, "0", mode3 == 2);
				}
				idxNextElement += 4;
				break;
			case 8:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				el2 = retrieveValue(array, idxNextElement + 2, mode2);
				el3 = readArrayValue(array, idxNextElement + 3);
				if (el1 == el2) {
					writeValue(array, el3, "1", mode3 == 2);
				} else {
					writeValue(array, el3, "0", mode3 == 2);
				}
				idxNextElement += 4;
				break;
			case 9:
				el1 = retrieveValue(array, idxNextElement + 1, mode1);
				relativeIdxBase += el1;
				idxNextElement += 2;
				break;
			default:
				failure(op, array, idxNextElement, nextElem, count, "Something went wrong!");
			}
			count++;

			nextElem = readArrayValue(array, idxNextElement);
			mode3 = new Double(nextElem / 10000).intValue();
			mode2 = new Double((nextElem - (mode3 * 10000)) / 1000).intValue();
			mode1 = new Double((nextElem - (mode3 * 10000) - (mode2 * 1000)) / 100).intValue();
			op = new Double((nextElem - (mode3 * 10000) - (mode2 * 1000) - (mode1 * 100))).intValue();
		}

		System.out.println("nextElem= " + nextElem);
		System.out.println("idxNextElement= " + idxNextElement);
		System.out.println("#iter= " + count);
		System.out.println("Array[idxNextElement]= " + readArrayValue(array, idxNextElement));

		throw new IllegalArgumentException("HALT");
	}

	private void failure(int op, String[] array, double idxNextElement2, double nextElem, int count, String msg) {
		System.out.println(msg);
		System.out.println("OP= " + op);
		System.out.println("A[idxNextElement+2]= " + readArrayValue(array, idxNextElement2 + 2));
		System.out.println("nextElem= " + nextElem);
		System.out.println("idxNextElement= " + idxNextElement2);
		System.out.println("#iter= " + count);
		System.exit(-1);
	}

	private double retrieveValue(String[] array, double idx, int mode) {
		assert (mode == 0 || mode == 1 || mode == 2);

		Double val = readArrayValue(array, idx);

		switch (mode) {
		case 0:
			return readArrayValue(array, val);
		case 1:
			return val;
		case 2:
			return readArrayValue(array, val + relativeIdxBase);
		}

		throw new IllegalArgumentException("Not possible be here");
	}

	private Double readArrayValue(String[] array, Double idx) {
		if (idx < array.length)
			return Double.parseDouble(array[idx.intValue()]);

		String moreValue = moreMemory.get(idx);
		if (moreValue == null)
			return new Double(0);

		return Double.parseDouble(moreValue);
	}

	private void writeValue(String[] array, Double idx, String value, boolean useRelative) {
		final Double actualIdx = (useRelative) ? (idx + relativeIdxBase) : idx;

		if (actualIdx < array.length)
			array[actualIdx.intValue()] = value;

		moreMemory.put(actualIdx, value);
	}
}

class Result11 {
	double res;
	boolean isStop;
	double idNextInstr;

	public Result11(double res, boolean isStop, double idNextInstr) {
		this.res = res;
		this.isStop = isStop;
		this.idNextInstr = idNextInstr;
	}
}

interface InputProvider {

	String provideInput() throws IOException;
}
