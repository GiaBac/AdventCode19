import java.util.ArrayList;
import java.util.List;

public class TestMainDay16 {

	public static void main(String[] args) {
		String fullInput = "59718730609456731351293131043954182702121108074562978243742884161871544398977055503320958653307507508966449714414337735187580549358362555889812919496045724040642138706110661041990885362374435198119936583163910712480088609327792784217885605021161016819501165393890652993818130542242768441596060007838133531024988331598293657823801146846652173678159937295632636340994166521987674402071483406418370292035144241585262551324299766286455164775266890428904814988362921594953203336562273760946178800473700853809323954113201123479775212494228741821718730597221148998454224256326346654873824296052279974200167736410629219931381311353792034748731880630444730593";
		String simpleInput = "03036732577212944063491565474664";
		List<Integer> input = createInput(fullInput, true);

		StringBuilder sb = new StringBuilder();
		sb.append(input.get(0));
		sb.append(input.get(1));
		sb.append(input.get(2));
		sb.append(input.get(3));
		sb.append(input.get(4));
		sb.append(input.get(5));
		sb.append(input.get(6));
		int offset = Integer.parseInt(sb.toString());

		List<Integer> basicPatter = new ArrayList<>();
		basicPatter.add(0);
		basicPatter.add(1);
		basicPatter.add(0);
		basicPatter.add(-1);

		int[] nextInput = removeOffset(input, offset);
		System.out.println("Input Complete, size=" + input.size() + " Offset=" + offset + " Remove offset Input size:"
				+ nextInput.length);

		int totPhases = 100;
		for (int idxPhase = 0; idxPhase < totPhases; idxPhase++) {
			nextInput = doFFTPhase(nextInput, basicPatter);

			System.out.println("Phase:" + (idxPhase + 1) + " res=" + nextInput[0] + nextInput[1] + nextInput[2]
					+ nextInput[3] + nextInput[4] + nextInput[5] + nextInput[6] + nextInput[7]);
		}

		System.out.println("Apply offset");
//		System.out.print(nextInput.get(offset));
//		System.out.print(nextInput.get(offset + 1));
//		System.out.print(nextInput.get(offset + 2));
//		System.out.print(nextInput.get(offset + 3));
//		System.out.print(nextInput.get(offset + 4));
//		System.out.print(nextInput.get(offset + 5));
//		System.out.print(nextInput.get(offset + 6));
//		System.out.print(nextInput.get(offset + 7));

	}

	private static int[] removeOffset(List<Integer> input, int offset) {
		int[] res = new int[input.size() - offset];
		for (int idx = offset, i = 0; idx < input.size(); idx++, i++)
			res[i] = input.get(idx);

		return res;
	}

	private static int[] doFFTPhase(int[] input, List<Integer> basicPatter) {
		int[] nextInput = input.clone();
		int idx = input.length - 2;

		while (idx >= 0) {
			nextInput[idx] = nextInput[idx] + nextInput[idx + 1];
			nextInput[idx] = Math.abs(nextInput[idx]) % 10;

			idx--;
		}

//		while (idx < input.size()) {
//			Integer res = doFFTElement(idx, input, basicPatter);
//			nextInput.add(res);
//			idx++;
//		}
		return nextInput;
	}

	private static Integer doFFTElement(int inputElIdx, List<Integer> input, List<Integer> basicPatter) {
		int step = inputElIdx + 1;
		int inputIdx = inputElIdx;
		int total = 0;

		while (inputIdx < input.size()) {
			int count = 0;
			while (count < step && inputIdx < input.size()) {
				total += input.get(inputIdx);
				count++;
				inputIdx++;
			}
			inputIdx += step;
			count = 0;
			while (count < step && inputIdx < input.size()) {
				total -= input.get(inputIdx);
				count++;
				inputIdx++;
			}
			inputIdx += step;
		}

		return Math.abs(total) % 10;
	}

	private static Integer calculatePatternEl(int patternIdx, int inputElIdx) {
		int elIdx = inputElIdx + 1;
		if (patternIdx < elIdx)
			return 0;

		final int mul;
		if (elIdx == 0) {
			mul = patternIdx;
		} else if (patternIdx % elIdx == 0) {
			mul = (patternIdx / elIdx);
		} else
			mul = (patternIdx / elIdx);

		int bucket = mul % 3;

		return (bucket == 0) ? 1 : (bucket == 1) ? 0 : -1;
	}

	private static List<Integer> createInput(String simpleInput, boolean part2) {
		ArrayList<Integer> res = new ArrayList<>();
		if (part2)
			for (int k = 0; k < 10000; k++)
				for (int i = 0; i < simpleInput.length(); i++)
					res.add(Character.getNumericValue(simpleInput.charAt(i)));
		else
			for (int i = 0; i < simpleInput.length(); i++)
				res.add(Character.getNumericValue(simpleInput.charAt(i)));

		return res;
	}
}
