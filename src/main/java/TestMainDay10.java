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

		Path path = FileSystems.getDefault().getPath(SIMPLE_INPUT);

		char[][] asteroidsBelt = loadAsteroidsBelt(path);

		System.out.println("Asteroid Belt = \n" + print(asteroidsBelt));

		char[][] clearOnePoint = clearAllStuffNotVisibleFrom(1, 3, asteroidsBelt.clone());

		System.out.println("Asteroid Belt clean for (1,3) = \n" + print(clearOnePoint));
	}

	private static char[][] clearAllStuffNotVisibleFrom(int rowFrom, int colFrom, char[][] clone) {
		// Same Row-Left
		for (int x = colFrom - 1; x >= 0; x--) {
			if (clone[rowFrom][x] == '#') {
				cleanAllStuffDueAPoint(rowFrom, colFrom, clone, rowFrom, x);
			}
		}

		// Same Row-Right
		for (int x = colFrom + 1; x < clone.length; x++) {
			if (clone[rowFrom][x] == '#') {
				cleanAllStuffDueAPoint(rowFrom, colFrom, clone, rowFrom, x);
			}
		}

		// Up
		for (int y = rowFrom - 1; y >= 0; y--)
			for (int x = colFrom + 1; x < clone.length; x++) {
				if (clone[y][x] == '#') {
					cleanAllStuffDueAPoint(rowFrom, colFrom, clone, y, x);
				}
			}

		return clone;
	}

	private static void cleanAllStuffDueAPoint(int rowFrom, int colFrom, char[][] clone, int rowTo, int colTo) {

		boolean goToLeft = colFrom - colTo >= 0;
		boolean goToUp = rowFrom - rowTo >= 0;

		// goto Left
		if (goToLeft) {
			for (int x = colFrom - 1; x >= 0; x--) {
				if (isBlockedBy(rowFrom, x, clone, rowFrom, colFrom, rowTo, colTo))
					clone[rowFrom][x] = 'X';
			}
		} else {
			// goto Right
			for (int x = colFrom + 1; x < clone.length; x++) {
				if (isBlockedBy(rowFrom, x, clone, rowFrom, colFrom, rowTo, colTo))
					clone[rowFrom][x] = 'X';
			}

		}
	}

	private static boolean isBlockedBy(int r, int c, char[][] clone, int rowFrom, int colFrom, int rowTo, int colTo) {
		if (colFrom == colTo)
			return c == colFrom;
		if (rowFrom == rowTo)
			return r == rowFrom;
		if (rowFrom == rowTo && colFrom == colTo)
			return false;

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
