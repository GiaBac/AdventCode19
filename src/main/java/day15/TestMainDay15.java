package day15;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TestMainDay15 {

	private static final String OX_SYMB = "O";
	static final Map<Integer, Map<Integer, String>> grid = new HashMap<>();
	static Integer maxRow = 0;
	static Integer maxCol = 0;
	static Integer minRow = 0;
	static Integer minCol = 0;
	static int droidX = 0;
	static int droidY = 0;
	static int oxygenX = 0;
	static int oxygenY = 0;
	static Integer lastStatus = -1;

	public static void main(String[] args) throws IOException {
		String intCodeInput = "3,1033,1008,1033,1,1032,1005,1032,31,1008,1033,2,1032,1005,1032,58,1008,1033,3,1032,1005,1032,81,1008,1033,4,1032,1005,1032,104,99,1002,1034,1,1039,1001,1036,0,1041,1001,1035,-1,1040,1008,1038,0,1043,102,-1,1043,1032,1,1037,1032,1042,1105,1,124,1002,1034,1,1039,1002,1036,1,1041,1001,1035,1,1040,1008,1038,0,1043,1,1037,1038,1042,1105,1,124,1001,1034,-1,1039,1008,1036,0,1041,1002,1035,1,1040,101,0,1038,1043,102,1,1037,1042,1105,1,124,1001,1034,1,1039,1008,1036,0,1041,101,0,1035,1040,1001,1038,0,1043,1002,1037,1,1042,1006,1039,217,1006,1040,217,1008,1039,40,1032,1005,1032,217,1008,1040,40,1032,1005,1032,217,1008,1039,1,1032,1006,1032,165,1008,1040,7,1032,1006,1032,165,1102,2,1,1044,1106,0,224,2,1041,1043,1032,1006,1032,179,1102,1,1,1044,1106,0,224,1,1041,1043,1032,1006,1032,217,1,1042,1043,1032,1001,1032,-1,1032,1002,1032,39,1032,1,1032,1039,1032,101,-1,1032,1032,101,252,1032,211,1007,0,45,1044,1106,0,224,1101,0,0,1044,1105,1,224,1006,1044,247,1002,1039,1,1034,1001,1040,0,1035,1002,1041,1,1036,101,0,1043,1038,1001,1042,0,1037,4,1044,1106,0,0,40,37,39,25,93,75,34,5,82,9,65,6,30,72,37,18,22,87,38,34,43,70,28,24,83,38,94,29,9,33,54,44,6,74,32,32,15,2,35,36,74,83,3,47,32,73,98,2,40,70,80,3,89,6,58,83,15,50,92,82,40,66,2,80,30,66,99,1,63,37,4,81,65,49,51,13,97,43,50,41,33,69,61,44,28,9,85,71,38,38,87,90,59,7,90,17,63,7,42,90,13,34,50,28,67,43,98,67,63,43,71,24,55,16,77,81,17,2,98,94,33,74,2,34,69,31,29,81,84,42,31,7,46,10,17,65,40,84,90,68,42,15,87,27,62,3,19,52,9,77,22,44,24,62,62,38,25,58,35,44,48,1,46,51,43,23,11,95,16,87,81,32,50,28,10,28,89,32,66,71,38,48,12,81,7,73,38,34,38,72,22,70,23,44,67,36,31,54,57,29,10,44,63,66,67,94,31,81,93,34,5,39,89,83,93,35,27,99,3,98,98,28,99,37,55,29,50,24,92,8,75,40,80,12,58,98,41,42,52,95,80,8,71,42,96,4,80,18,53,50,79,35,37,87,39,89,28,9,66,21,74,19,77,79,23,92,36,47,11,67,35,76,28,42,33,90,88,18,7,55,5,75,10,60,17,89,31,80,38,77,41,65,41,98,49,39,77,14,82,24,34,53,27,73,91,86,22,87,83,5,81,36,90,12,30,85,49,83,44,39,58,42,53,5,73,15,67,17,98,35,30,72,81,33,78,7,99,83,18,76,15,36,49,40,66,36,65,9,53,95,21,30,22,85,91,3,28,97,84,31,32,52,14,64,15,4,69,12,56,71,1,11,47,22,29,32,71,30,78,53,23,81,30,44,92,41,42,56,11,16,6,80,29,18,72,66,68,4,36,94,36,20,10,75,79,35,17,62,6,80,46,76,34,96,31,74,11,56,3,18,66,30,73,65,18,99,14,61,7,26,51,11,92,55,29,3,9,89,96,24,67,21,85,7,23,75,71,26,19,43,81,2,89,36,82,23,81,18,60,67,25,56,43,27,77,42,44,86,2,90,23,81,1,41,93,81,40,99,6,66,77,17,95,47,4,44,48,51,78,16,78,51,34,82,3,67,67,27,55,14,36,84,5,79,47,12,31,86,23,54,92,27,71,12,40,58,50,42,78,25,27,89,41,55,59,40,30,55,6,70,9,95,86,51,27,91,15,32,47,79,20,47,90,14,10,49,35,2,96,16,20,68,43,6,2,52,11,71,26,79,88,28,57,31,47,12,26,2,59,30,68,16,34,3,84,43,82,29,61,25,9,55,74,6,9,12,46,16,40,46,90,33,63,57,2,90,68,92,29,55,44,36,25,3,47,29,57,44,12,5,99,95,78,4,9,28,48,5,27,77,39,97,79,39,49,99,40,47,91,13,77,28,51,36,62,25,68,18,6,65,79,65,3,47,53,81,32,95,59,33,84,40,73,59,10,46,57,50,36,44,62,42,48,24,36,63,59,1,31,58,24,29,76,2,40,31,72,47,27,72,42,41,60,4,14,58,99,34,94,44,41,97,35,6,51,10,23,53,80,5,39,16,18,12,91,36,95,51,38,1,49,86,35,71,6,82,15,30,15,92,65,76,81,19,71,32,12,89,40,91,89,2,89,62,67,5,17,54,73,70,16,78,10,55,43,97,78,59,29,95,39,54,80,76,37,95,92,79,16,50,21,80,11,55,13,73,57,60,3,84,4,61,19,63,12,82,22,53,31,63,0,0,21,21,1,10,1,0,0,0,0,0,0";
		String[] array = intCodeInput.split(",");

		DroidInputProvider inputProvider = new DroidInputProvider(true, grid);
		CompleteIntCodeMachine_Day15 pc = new CompleteIntCodeMachine_Day15(array, inputProvider);
		grid.computeIfAbsent(droidY, f -> new HashMap<>()).put(droidX, "D");
		boolean complete = false;
		try {
			while (!complete) {
				Result11 resX = pc.runIntCodeProgram();
				lastStatus = new Double(resX.res).intValue();

				System.out.println("X=" + droidX + " Y=" + droidY + " Res=" + lastStatus);

				if (lastStatus == 2) {
					System.out.println("Oxygen Found!");
					oxygenX = droidX;
					oxygenY = droidY;
					moveDroid(lastStatus, inputProvider);
					printGrid();
					inputProvider.waitTime(5000);
				} else
					moveDroid(lastStatus, inputProvider);
			}
		} catch (IllegalArgumentException e) {
			if (e.getMessage().equals("HALT")) {
				printGrid();
				System.out.println("Halt correctly.");
			} else {
				System.out.println("Halt NOT correctly. Something goes wrong!\n" + e);
			}
		} catch (IOException e) {
			System.out.println("Halt NOT correctly. Something goes wrong!\n" + e);
		} catch (AllDiscoveredException e) {
			System.out.println("All dicovered! Halt correctly. Start calculates oxygen spread!\n");
			calculateOxygenSpreadTime();
		}
	}

	private static void calculateOxygenSpreadTime() {
		printGrid();
		System.out.println("Oxygen position: <X=" + oxygenX + " Y=" + oxygenY + ">");
		Map<Integer, Map<Integer, Integer>> nextSpreadOxigen = new HashMap<>();
		int totalTime = 0;

		while (!oxygenSpreadAll()) {
			int idxRow = minRow;
			while (idxRow <= maxRow) {
				int idxCol = minCol;
				while (idxCol <= maxCol) {
					Map<Integer, String> row = grid.get(idxRow);
					String cell = row.get(idxCol);
					if (cell != null && cell.equals(OX_SYMB))
						calcSpreadOxygen(idxRow, idxCol, nextSpreadOxigen);

					idxCol++;
				}
				idxRow++;
			}

			for (Entry<Integer, Map<Integer, Integer>> yxValue : nextSpreadOxigen.entrySet()) {
				for (Integer x : yxValue.getValue().keySet()) {
					grid.get(yxValue.getKey()).put(x, OX_SYMB);
				}
			}

			totalTime++;
			System.out.println("Total Time to spread#" + totalTime);
			printGrid();
			DroidInputProvider.waitTime(10);
		}
	}

	private static boolean oxygenSpreadAll() {
		for (Map<Integer, String> rows : grid.values()) {
			for (String cell : rows.values()) {
				if (cell != null && cell.equals("."))
					return false;
			}
		}

		return true;
	}

	private static void calcSpreadOxygen(int idxRow, int idxCol, Map<Integer, Map<Integer, Integer>> nextSpreadOxigen) {
		trySpread(idxRow, idxCol + 1, nextSpreadOxigen);
		trySpread(idxRow, idxCol - 1, nextSpreadOxigen);
		trySpread(idxRow + 1, idxCol, nextSpreadOxigen);
		trySpread(idxRow - 1, idxCol, nextSpreadOxigen);
	}

	private static void trySpread(int y, int x, Map<Integer, Map<Integer, Integer>> nextSpreadOxigen) {
		Map<Integer, String> row = grid.get(y);

		String c = (row != null) ? row.get(x) : null;
		if (c != null && (c.equals(".") || c.equals("D")))
			nextSpreadOxigen.computeIfAbsent(y, k -> new HashMap<>()).put(x, 1);
	}

	private static void moveDroid(Integer newStatus, DroidInputProvider inputProvider) {
		int newX = droidX;
		int newY = droidY;
		if (inputProvider.lastInput == 1)
			newY--;
		else if (inputProvider.lastInput == 2)
			newY++;
		else if (inputProvider.lastInput == 3)
			newX--;
		else if (inputProvider.lastInput == 4)
			newX++;

		if (newStatus == 0)
			grid.computeIfAbsent(newY, f -> new HashMap<>()).put(newX, "#");
		else if (newStatus == 1) {
			grid.computeIfAbsent(newY, f -> new HashMap<>()).put(newX, "D");
			if (!grid.get(droidY).get(droidX).equals(OX_SYMB))
				grid.computeIfAbsent(droidY, f -> new HashMap<>()).put(droidX, ".");
			droidX = newX;
			droidY = newY;
		} else if (newStatus == 2) {
			grid.computeIfAbsent(newY, f -> new HashMap<>()).put(newX, OX_SYMB);
			droidX = newX;
			droidY = newY;
		}

		adjustGridBorder(newX, newY);
	}

	private static void adjustGridBorder(Integer X, Integer Y) {
		if (X > maxCol)
			maxCol = X;
		if (X < minCol)
			minCol = X;
		if (Y > maxRow)
			maxRow = Y;
		if (Y < minRow)
			minRow = Y;
	}

	public static void printGrid() {
		int idxRow = minRow;
		StringBuilder sb = new StringBuilder();
		while (idxRow <= maxRow) {
			int idxCol = minCol;
			while (idxCol <= maxCol) {

				Map<Integer, String> row = grid.get(idxRow);
				String cell = row.get(idxCol);
				sb.append((cell == null) ? "?" : cell);
				idxCol++;
			}
			sb.append("\n");
			idxRow++;
		}
		System.out.println(sb.toString());
	}
}