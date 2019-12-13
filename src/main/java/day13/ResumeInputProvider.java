package day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ResumeInputProvider implements InputProvider {

	String[] resumeInputs;
	int inputIdx = 0;
	private BufferedReader joystick;
	private final int historySize;
	private int wait;
	private int d;
	private int a;

	public ResumeInputProvider(List<String> inputHistory) {
		resumeInputs = inputHistory.toArray(new String[0]);
		historySize = inputHistory.size();
		joystick = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public String provideInput() throws IOException {
		TestMainDay13.printGrid();
		System.out.println("Move!");

		if (wait != 0) {
			wait--;
			return moveJoystick("");
		} else if (d != 0) {
			d--;
			return moveJoystick("d");
		} else if (a != 0) {
			a--;
			return moveJoystick("a");
		}

		if (inputIdx < historySize) {
			String ret = resumeInputs[inputIdx];
			if (ret.equals("w")) {
				wait = 10;
				ret = "";
			} else if (ret.equals("e")) {
				d = 10;
				ret = "d";
			} else if (ret.equals("q")) {
				a = 10;
				ret = "a";
			}

			inputIdx++;
			return moveJoystick(ret);
		}

		String input = joystick.readLine();
		TestMainDay13.inputHistory.add(input);
		if (input.equals("w")) {
			wait = 10;
			input = "";
		} else if (input.equals("e")) {
			d = 10;
			input = "d";
		} else if (input.equals("q")) {
			a = 10;
			input = "a";
		}

		return moveJoystick(input);
	}

	private String moveJoystick(String input) {
		return (input.isEmpty()) ? "0" : (input.equals("a")) ? "-1" : "1";
	}
}
