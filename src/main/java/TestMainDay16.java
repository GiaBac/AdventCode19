import java.util.ArrayList;
import java.util.List;

public class TestMainDay16 {

	public static void main(String[] args) {
		String fullInput = "59718730609456731351293131043954182702121108074562978243742884161871544398977055503320958653307507508966449714414337735187580549358362555889812919496045724040642138706110661041990885362374435198119936583163910712480088609327792784217885605021161016819501165393890652993818130542242768441596060007838133531024988331598293657823801146846652173678159937295632636340994166521987674402071483406418370292035144241585262551324299766286455164775266890428904814988362921594953203336562273760946178800473700853809323954113201123479775212494228741821718730597221148998454224256326346654873824296052279974200167736410629219931381311353792034748731880630444730593";
		String simpleInput = "03036732577212944063491565474664";
		List<Integer> input = createInput(simpleInput);

		System.out.println("Input Complete, size=" + input.size());

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

		List<Integer> nextInput = input;
		int totPhases = 100;
		for (int idxPhase = 0; idxPhase < totPhases; idxPhase++) {
			nextInput = doFFTPhase(nextInput, basicPatter);

			System.out.println("Phase:" + (idxPhase + 1) + " res=" + nextInput);
		}

		System.out.println("Apply offset");
		System.out.print(nextInput.get(offset));
		System.out.print(nextInput.get(offset + 1));
		System.out.print(nextInput.get(offset + 2));
		System.out.print(nextInput.get(offset + 3));
		System.out.print(nextInput.get(offset + 4));
		System.out.print(nextInput.get(offset + 5));
		System.out.print(nextInput.get(offset + 6));
		System.out.print(nextInput.get(offset + 7));

	}

	private static List<Integer> doFFTPhase(List<Integer> input, List<Integer> basicPatter) {
		List<Integer> nextInput = new ArrayList<>();
		int idx = 0;
		while (idx < input.size()) {
			Integer res = doFFTElement(idx, input, basicPatter);
			nextInput.add(res);
			idx++;
		}
		return nextInput;
	}

	private static Integer doFFTElement(int inputElIdx, List<Integer> input, List<Integer> basicPatter) {
		List<Integer> res = new ArrayList<>();
		List<Integer> pattern = calculatePattern(basicPatter, inputElIdx);
		System.out.println("inputElIdx: " + inputElIdx + " NewPatternSize=" + pattern.size());

		int inputIdx = 0;
		boolean skipOnlyTheFirstPattern = true;
		while (inputIdx < input.size()) {
			int patternIdx = 0;
			while (patternIdx < pattern.size() && inputIdx < input.size()) {
				if (skipOnlyTheFirstPattern) {
					patternIdx++;
					skipOnlyTheFirstPattern = false;
				} else {
					res.add(input.get(inputIdx) * pattern.get(patternIdx));
					//res.add(input.get(inputIdx) * calculatePatternEl(patternIdx, basicPatter, inputElIdx));
					patternIdx++;
					inputIdx++;
				}
			}
		}

		int total = 0;
		for (Integer el : res) {
			total += el;
		}

		return Math.abs(total) % 10;
	}

	private static Integer calculatePatternEl(int patternIdx, List<Integer> basicPatter, int inputElIdx) {
		return null;
	}

	private static List<Integer> calculatePattern(List<Integer> basicPatter, int phaseIdx) {

		List<Integer> newPattern = new ArrayList<>();
		for (Integer p : basicPatter) {
			int repeatCount = phaseIdx + 1;
			while (repeatCount > 0) {
				newPattern.add(p);
				repeatCount--;
			}
		}

		return newPattern;
	}

	private static List<Integer> createInput(String simpleInput) {
		ArrayList<Integer> res = new ArrayList<>();

		for (int k = 0; k < 10000; k++)
			for (int i = 0; i < simpleInput.length(); i++)
				res.add(Character.getNumericValue(simpleInput.charAt(i)));

		return res;
	}
}
