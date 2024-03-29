import java.util.HashMap;
import java.util.Map;

public class TestMainDay11 {

	static final Map<Integer, Map<Integer, String>> grid = new HashMap<>();
	static Integer currRow = 0;
	static Integer currCol = 0;
	static Integer maxRow = 0;
	static Integer maxCol = 0;
	static Integer minRow = 0;
	static Integer minCol = 0;
	static char direction = '^';

	public static void main(String[] args) {
		String intCodeInput = "3,8,1005,8,318,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,1,10,4,10,101,0,8,29,1,107,12,10,2,1003,8,10,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,1002,8,1,59,1,108,18,10,2,6,7,10,2,1006,3,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1002,8,1,93,1,1102,11,10,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,101,0,8,118,2,1102,10,10,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,0,10,4,10,101,0,8,145,1006,0,17,1006,0,67,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,0,10,4,10,101,0,8,173,2,1109,4,10,1006,0,20,3,8,102,-1,8,10,1001,10,1,10,4,10,108,0,8,10,4,10,102,1,8,201,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,0,10,4,10,1002,8,1,224,1006,0,6,1,1008,17,10,2,101,5,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,1,8,10,4,10,1001,8,0,256,2,1107,7,10,1,2,4,10,2,2,12,10,1006,0,82,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1002,8,1,294,2,1107,2,10,101,1,9,9,1007,9,988,10,1005,10,15,99,109,640,104,0,104,1,21102,1,837548352256,1,21102,335,1,0,1105,1,439,21102,1,47677543180,1,21102,346,1,0,1106,0,439,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21102,1,235190374592,1,21101,393,0,0,1105,1,439,21102,3451060455,1,1,21102,404,1,0,1105,1,439,3,10,104,0,104,0,3,10,104,0,104,0,21102,837896909668,1,1,21102,1,427,0,1105,1,439,21102,1,709580555020,1,21102,438,1,0,1105,1,439,99,109,2,21201,-1,0,1,21102,1,40,2,21102,1,470,3,21102,460,1,0,1106,0,503,109,-2,2105,1,0,0,1,0,0,1,109,2,3,10,204,-1,1001,465,466,481,4,0,1001,465,1,465,108,4,465,10,1006,10,497,1101,0,0,465,109,-2,2105,1,0,0,109,4,1201,-1,0,502,1207,-3,0,10,1006,10,520,21101,0,0,-3,21202,-3,1,1,22101,0,-2,2,21101,1,0,3,21101,0,539,0,1106,0,544,109,-4,2105,1,0,109,5,1207,-3,1,10,1006,10,567,2207,-4,-2,10,1006,10,567,21202,-4,1,-4,1105,1,635,22101,0,-4,1,21201,-3,-1,2,21202,-2,2,3,21101,0,586,0,1105,1,544,22102,1,1,-4,21102,1,1,-1,2207,-4,-2,10,1006,10,605,21102,1,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,627,21202,-1,1,1,21101,627,0,0,105,1,502,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2105,1,0";
		String sampleCodeInput = "1102,34915192,34915192,7,4,7,99,0";
		String[] array = intCodeInput.split(",");

		CompleteIntCodeMachine_Day11 pc = new CompleteIntCodeMachine_Day11(array, new InputProvider() {
			@Override
			public String provideInput() {
				Map<Integer, String> r = grid.get(currRow);
				if (r == null)
					return "0";
				String p = r.get(currCol);
				if (p == null || p.isEmpty())
					return "0";

				return p;
			}
		});

		grid.computeIfAbsent(currRow, f -> new HashMap<>()).put(currCol, "1");

		try {
			while (1 == 1) {
				Result11 resColour = pc.runIntCodeProgram();
				System.out.println("RES-COL= " + resColour.res);
				Integer resInt = new Double(resColour.res).intValue();
				grid.computeIfAbsent(currRow, f -> new HashMap<>()).put(currCol, resInt.toString());

				Result11 resDir = pc.runIntCodeProgram();
				System.out.println("RES-DIR= " + resColour.res);
				if (resDir.res == 0) {
					direction = turnRight(direction);
				} else {
					direction = turnLeft(direction);
				}

				moveRobot();
			}
		} catch (IllegalArgumentException e) {
			if (e.getMessage().equals("HALT")) {
				int tot = 0;
				for (Map<Integer, String> el : grid.values()) {
					tot += el.size();
				}
				System.out.println("Halt correctly. Map size=" + tot);
				printGrid();
			} else {
				System.out.println("Halt NOT correctly. Something goes wrong!\n" + e);
			}
		}
	}

	private static void printGrid() {
		int idxRow = maxRow;
		StringBuilder sb = new StringBuilder();
		while (idxRow >= minRow) {
			int idxCol = minCol;
			while (idxCol <= maxCol) {
				sb.append(grid.get(idxRow).get(idxCol));
				idxCol++;
			}
			sb.append("\n");
			idxRow--;
		}

		System.out.println(sb.toString());
		System.out.println("It's a mirror!!");
	}

	private static void moveRobot() {
		switch (direction) {
		case '^':
			currRow++;
			break;
		case '<':
			currCol--;
			break;
		case 'v':
			currRow--;
			break;
		case '>':
			currCol++;
			break;
		default:
			throw new IllegalStateException("Impossible direction: " + direction);
		}

		if (currCol < minCol)
			minCol = currCol;
		if (currCol > maxCol)
			maxCol = currCol;
		if (currRow < minRow)
			minRow = currRow;
		if (currRow > maxRow)
			maxRow = currRow;
	}

	private static char turnLeft(char direction) {
		switch (direction) {
		case '^':
			return '<';
		case '<':
			return 'v';
		case 'v':
			return '>';
		case '>':
			return '^';
		default:
			throw new IllegalStateException("Impossible direction: " + direction);
		}
	}

	private static char turnRight(char direction) {
		switch (direction) {
		case '^':
			return '>';
		case '>':
			return 'v';
		case 'v':
			return '<';
		case '<':
			return '^';
		default:
			throw new IllegalStateException("Impossible direction: " + direction);
		}
	}

}