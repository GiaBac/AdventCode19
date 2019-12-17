package day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class DroidInputProvider implements InputProvider {
	private BufferedReader joystick;

	public DroidInputProvider(boolean autoplay, Map<Integer, Map<Integer, String>> grid) {
		joystick = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public String provideInput() throws IOException {

		return manualPlay();
	}

	public static void waitTime(int millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String manualPlay() throws IOException {
		System.out.println("Manual action required");
		String input = joystick.readLine();
		return input;
	}
}
