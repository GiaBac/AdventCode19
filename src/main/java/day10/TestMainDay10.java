package day10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TestMainDay10 {

	private static final int MAX_SIZE_BELT = 40;
	private static final String PUZZLE_INPUT = "puzzleInputD10.txt";
	private static final String SIMPLE_INPUT = "simpleD10.txt";

	public static void main(String[] args) throws IOException {

		Path path = FileSystems.getDefault().getPath(PUZZLE_INPUT);

		char[][] asteroidsBelt = loadAsteroidsBelt(path);

		System.out.println("Asteroid Belt = \n" + print(asteroidsBelt));

		int maxVisible = Integer.MIN_VALUE;
		int maxY = 0;
		int maxX = 0;
		for (int y = 0; y < asteroidsBelt.length; y++) {
			for (int x = 0; x < asteroidsBelt[0].length; x++) {
				if (asteroidsBelt[y][x] != '#')
					continue;

				char[][] clearOnePoint = clearAllStuffNotVisibleFrom(y, x, clone(asteroidsBelt));
				System.out.println("Asteroid Belt clean for (" + y + "," + x + ") = \n" + print(clearOnePoint));

				int numAstVisible = countAstVisible(clearOnePoint);
				System.out.println("Asteroid Belt clean for (" + y + "," + x + "), numVisibleAst=" + numAstVisible);

				if (numAstVisible > maxVisible) {
					maxVisible = numAstVisible;
					maxX = x;
					maxY = y;
				}
			}
		}

		System.out.println("Best (" + maxY + "," + maxX + "), numVisibleAst=" + maxVisible);
		char[][] clearOnePoint = clearAllStuffNotVisibleFrom(maxY, maxX, clone(asteroidsBelt));
		System.out.println("Asteroid Belt clean for (" + maxY + "," + maxX + ") = \n" + print(clearOnePoint));

		guess200th(clearOnePoint, maxY, maxX);
	}

	private static void guess200th(char[][] clearOnePoint, int maxY, int maxX) {
		double angleDegrees = 0;
		double angle = angleDegrees * Math.PI / 180;

		Double endX = maxX + 1000 * Math.sin(angle);
		Double endY = maxY + 1000 * Math.cos(angle);

		int numHit = 1;
		boolean isHit = deleteFirst(maxY, maxX, clearOnePoint, endY.intValue(), endX.intValue());
		System.out.println("Is Hit=" + isHit + "\n" + print(clearOnePoint));

	}

	private static boolean deleteFirst(int rowFrom, int colFrom, char[][] clone, int rowTo, int colTo) {
		int a = rowTo - rowFrom;
		int b = colFrom - colTo;
		int c = a * colFrom + b * rowFrom;

		boolean goToLeft = colFrom - colTo > 0;
		boolean goToUp = rowFrom - rowTo > 0;

		if (a == 0) {
			int X = (goToLeft) ? colFrom - 1 : colFrom + 1;
			while ((0 <= X) && (X < clone[0].length)) {
				if (clone[rowFrom][X] == 'T') {
					clone[rowFrom][X] = 'H';
					return true;
				}

				if (goToLeft)
					X--;
				else
					X++;
			}
		} else if (b == 0) {
			int Y = (goToUp) ? rowFrom - 1 : rowFrom + 1;
			while ((0 <= Y) && (Y < clone.length)) {
				if (clone[Y][colFrom] == 'T') {
					clone[Y][colFrom] = 'H';
					return true;
				}

				if (goToUp)
					Y--;
				else
					Y++;
			}
		} else {
			// Diagonal case

			int X = (goToLeft) ? colFrom - 1 : colFrom + 1;
			while ((0 <= X) && (X < clone[0].length)) {
				int temp = (a * X - c);
				if (temp % b == 0) {
					int Y = (a * X - c) / ((-1) * b);
					if ((0 <= Y) && (Y < clone.length))
						if (clone[Y][X] == 'T') {
							clone[Y][X] = 'H';
							return true;
						}
				} else {
					/* No hit */}

				if (goToLeft)
					X--;
				else
					X++;
			}
		}

		return false;
	}

	private static char[][] clone(char[][] asteroidsBelt) {
		int maxRow = asteroidsBelt.length;
		int maxCol = asteroidsBelt[0].length;
		char[][] clone = new char[maxRow][maxCol];

		for (int r = 0; r < maxRow; r++)
			for (int c = 0; c < maxCol; c++)
				clone[r][c] = asteroidsBelt[r][c];

		return clone;
	}

	private static int countAstVisible(char[][] clearOnePoint) {
		int tot = 0;
		for (char[] cs : clearOnePoint) {
			for (char p : cs) {
				if (p == 'T')
					tot++;
			}
		}
		return tot;
	}

	private static char[][] clearAllStuffNotVisibleFrom(int rowFrom, int colFrom, char[][] clone) {
		clone[rowFrom][colFrom] = 'A';
		// Same Row-Left
		for (int x = colFrom - 1; x >= 0; x--) {
			if (clone[rowFrom][x] == '#') {
				markBlockedByAPointDirect(rowFrom, colFrom, clone, rowFrom, x);
			}
		}

		// Same Row-Right
		for (int x = colFrom + 1; x < clone[0].length; x++) {
			if (clone[rowFrom][x] == '#') {
				markBlockedByAPointDirect(rowFrom, colFrom, clone, rowFrom, x);
			}
		}

		// Same Col-Up
		for (int y = rowFrom - 1; y >= 0; y--) {
			if (clone[y][colFrom] == '#') {
				markBlockedByAPointDirect(rowFrom, colFrom, clone, y, colFrom);
			}
		}

		// Same Col-Down
		for (int y = rowFrom + 1; y < clone.length; y++) {
			if (clone[y][colFrom] == '#') {
				markBlockedByAPointDirect(rowFrom, colFrom, clone, y, colFrom);
			}
		}

		// Down
		for (int y = rowFrom + 1; y < clone.length; y++)
			for (int x = 0; x < clone[0].length; x++) {
				if (clone[y][x] == '#') {
					markBlockedByAPointDirect(rowFrom, colFrom, clone, y, x);
				}
			}

		// Up
		for (int y = rowFrom - 1; y >= 0; y--)
			for (int x = 0; x < clone[0].length; x++) {
				if (clone[y][x] == '#') {
					markBlockedByAPointDirect(rowFrom, colFrom, clone, y, x);
				}
			}

		return clone;
	}

	private static void markBlockedByAPointDirect(int rowFrom, int colFrom, char[][] clone, int rowTo, int colTo) {
		clone[rowTo][colTo] = 'T';

		int a = rowTo - rowFrom;
		int b = colFrom - colTo;
		int c = a * colFrom + b * rowFrom;

		boolean goToLeft = colFrom - colTo > 0;
		boolean goToUp = rowFrom - rowTo > 0;

		if (a == 0) {
			int X = (goToLeft) ? colTo - 1 : colTo + 1;
			while ((0 <= X) && (X < clone[0].length)) {
				clone[rowFrom][X] = 'X';
				if (goToLeft)
					X--;
				else
					X++;
			}
		} else if (b == 0) {
			int Y = (goToUp) ? rowTo - 1 : rowTo + 1;
			while ((0 <= Y) && (Y < clone.length)) {
				clone[Y][colFrom] = 'X';
				if (goToUp)
					Y--;
				else
					Y++;
			}
		} else {
			// Diagonal case

			int X = (goToLeft) ? colTo - 1 : colTo + 1;
			while ((0 <= X) && (X < clone[0].length)) {
				int temp = (a * X - c);
				if (temp % b == 0) {
					int Y = (a * X - c) / ((-1) * b);
					if ((0 <= Y) && (Y < clone.length))
						clone[Y][X] = 'X';
				} else {
					/* No hit */}

				if (goToLeft)
					X--;
				else
					X++;
			}
		}
	}

	private static void markBlockedByAPoint(int rowFrom, int colFrom, char[][] clone, int rowTo, int colTo) {

		boolean onSameCol = colFrom - colTo == 0;
		boolean onSameRow = rowFrom - rowTo == 0;
		boolean goToLeft = colFrom - colTo > 0;
		boolean goToUp = rowFrom - rowTo > 0;
		int Y = (onSameRow) ? rowTo : (goToUp) ? (rowTo - 1) : (rowTo + 1);

		System.out.println("Asteroid Belt clean for (1,3) Start Node (y=" + rowTo + ",x=" + colTo + "), GoUp=" + goToUp
				+ " GoLeft=" + goToLeft);

		while ((0 <= Y) && (Y < clone.length)) {
			int X = (onSameCol) ? colTo : (goToLeft) ? colTo - 1 : colTo + 1;

			while ((0 <= X) && (X < clone[0].length)) {
				if (isBlockedBy(Y, X, clone, rowFrom, colFrom, rowTo, colTo))
					clone[Y][X] = 'X';

				if (onSameCol)
					X = -1;
				else if (goToLeft)
					X--;
				else
					X++;
			}

			if (onSameRow)
				Y = -1;
			else if (goToUp)
				Y--;
			else
				Y++;
		}

		System.out
				.println("Asteroid Belt clean for (1,3) Start Node (y=" + rowTo + ",x=" + colTo + ")\n" + print(clone));
	}

	private static boolean isBlockedBy(int r, int c, char[][] clone, int rowFrom, int colFrom, int rowTo, int colTo) {
		if (rowFrom == rowTo && colFrom == colTo)
			return false;
		if (r == rowTo && c == colTo)
			return false;

		if (colFrom == colTo)
			return c == colFrom;
		if (rowFrom == rowTo)
			return r == rowFrom;

		return (c - colFrom) / (colFrom - colTo) == (r - rowFrom) / (rowFrom - rowTo);
	}

	private static char[][] loadAsteroidsBelt(Path path) throws IOException {
		char[][] asteroidsBelt = new char[MAX_SIZE_BELT][MAX_SIZE_BELT];
		Iterator<String> it = Files.lines(path, StandardCharsets.UTF_8).iterator();
		int y = 0;
		int maxRow = 0;
		int maxCol = 0;
		while (it.hasNext()) {
			String line = it.next();
			int x = 0;
			while (x < line.length()) {
				asteroidsBelt[y][x] = line.charAt(x);
				x++;
			}
			y++;
			maxRow++;
			maxCol = x;
		}

		char[][] realSize = new char[maxRow][maxCol];
		for (int r = 0; r < maxRow; r++)
			for (int c = 0; c < maxCol; c++)
				realSize[r][c] = asteroidsBelt[r][c];

		return realSize;
	}

	private static String print(char[][] asteroidsBelt) {
		StringBuilder sb = new StringBuilder();
		for (char[] cs : asteroidsBelt) {
			for (char c : cs) {
				sb.append(c);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
