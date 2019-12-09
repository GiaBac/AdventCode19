import java.util.ArrayList;
import java.util.List;

public class TestMainDay7 {

	public static void main(String[] args) {
		String intCodeInput = "3,8,1001,8,10,8,105,1,0,0,21,42,67,88,105,114,195,276,357,438,99999,3,9,101,4,9,9,102,3,9,9,1001,9,2,9,102,4,9,9,4,9,99,3,9,1001,9,4,9,102,4,9,9,101,2,9,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,1001,9,4,9,1002,9,4,9,101,2,9,9,1002,9,2,9,4,9,99,3,9,101,4,9,9,102,3,9,9,1001,9,5,9,4,9,99,3,9,102,5,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,99";
		String sampleCodeInput = "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10";
		String[] array = intCodeInput.split(",");

		List<String> phases = new ArrayList<>();
		phases.add("5");
		phases.add("6");
		phases.add("7");
		phases.add("8");
		phases.add("9");

		List<List<String>> allList = generateAllSequence(phases);

		System.out.println(allList.toString());
		System.out.println(allList.size());

		int maxTS = Integer.MIN_VALUE;
		List<String> seqMax = null;
		for (List<String> seq : allList) {
			Result res = new Result(0, false, 0);
			Amplifier_Day7 aA = new Amplifier_Day7(0, array.clone());
			Amplifier_Day7 aB = new Amplifier_Day7(0, array.clone());
			Amplifier_Day7 aC = new Amplifier_Day7(0, array.clone());
			Amplifier_Day7 aD = new Amplifier_Day7(0, array.clone());
			Amplifier_Day7 aE = new Amplifier_Day7(0, array.clone());
			boolean first = true;
			try {
				while (1 == 1) {
					Result resA = aA.runIntCodeProgram(seq.get(0), Integer.toString(res.res), first);
					Result resB = aB.runIntCodeProgram(seq.get(1), Integer.toString(resA.res), first);
					Result resC = aC.runIntCodeProgram(seq.get(2), Integer.toString(resB.res), first);
					Result resD = aD.runIntCodeProgram(seq.get(3), Integer.toString(resC.res), first);
					res = aE.runIntCodeProgram(seq.get(4), Integer.toString(resD.res), first);
					first = false;
				}
			} catch (IllegalArgumentException e) {

			}

			System.out.println("Complete Seq: " + res.res);
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
}

class Result {
	int res;
	boolean isStop;
	int idNextInstr;

	public Result(int res, boolean isStop, int idNextInstr) {
		this.res = res;
		this.isStop = isStop;
		this.idNextInstr = idNextInstr;
	}
}
