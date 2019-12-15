package day15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DroidInputProvider implements InputProvider {

	int inputIdx = 0;
	private BufferedReader joystick;
	private boolean autoplay;
	public int lastInput;
	private Map<Integer, Map<Integer, String>> grid;
	private List<Integer> currPath;
	private boolean backTrack;

	public DroidInputProvider(boolean autoplay, Map<Integer, Map<Integer, String>> grid) {
		this.autoplay = autoplay;
		this.grid = grid;
		joystick = new BufferedReader(new InputStreamReader(System.in));
		currPath = new ArrayList<>();
	}

	@Override
	public String provideInput() throws IOException, AllDiscoveredException {
		TestMainDay15.printGrid();

		if ((TestMainDay15.lastStatus == 1 || TestMainDay15.lastStatus == 2) && !backTrack)
			currPath.add(lastInput);

		if (backTrack) {
			currPath.remove(currPath.size() - 1);
			backTrack = false;
		}

		String input = (autoplay) ? autoPlayMove() : manualPlay();
		if (autoplay)
			waitTime(5);
		String adjInput = adjustInput(input);
		lastInput = Integer.parseInt(adjInput);
		return adjInput;
	}

	public static void waitTime(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String adjustInput(String input) {
		if (input.equals("w"))
			return "1";
		if (input.equals("s"))
			return "2";
		if (input.equals("a"))
			return "3";
		if (input.equals("d"))
			return "4";

		return "";
	}

	private String autoPlayMove() throws IOException, AllDiscoveredException {
		String nextInput = findAFreeWay(TestMainDay15.droidX, TestMainDay15.droidY);
		if (nextInput == null) {
			backTrack = true;
			nextInput = reverseLast();
		}
		return (nextInput == null) ? manualPlay() : nextInput;
	}

	private String reverseLast() throws AllDiscoveredException {
		if (currPath.isEmpty())
			throw new AllDiscoveredException();
		Integer last = currPath.get(currPath.size() - 1);

		if (last == 1)
			return "s";
		if (last == 2)
			return "w";
		if (last == 3)
			return "d";
		if (last == 4)
			return "a";

		return null;
	}

	private String findAFreeWay(int droidX, int droidY) {

		if (tryDirection(droidX, droidY - 1))
			return "w";
		if (tryDirection(droidX, droidY + 1))
			return "s";
		if (tryDirection(droidX - 1, droidY))
			return "a";
		if (tryDirection(droidX + 1, droidY))
			return "d";

		return null;
	}

	private boolean tryDirection(int X, int Y) {
		Map<Integer, String> row = grid.get(Y);
		if (row == null)
			return true;

		String cell = row.get(X);
		if (cell == null)
			return true;

		return false;
	}

	private String manualPlay() throws IOException {
		System.out.println("Manual action required");
		String input = joystick.readLine();
		return input;
	}
}
