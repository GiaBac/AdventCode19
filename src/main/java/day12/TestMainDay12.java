package day12;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestMainDay12 {

	public static void main(String[] args) {
		Moon i = new Moon(-8, -18, 6);
		Moon e = new Moon(-11, -14, 4);
		Moon g = new Moon(8, -3, -10);
		Moon c = new Moon(-2, -16, 1);

//		Moon i = new Moon(-1, 0, 2);
//		Moon e = new Moon(2, -10, -7);
//		Moon g = new Moon(4, -8, 8);
//		Moon c = new Moon(3, 5, -1);

		Moon[] allMoon = new Moon[4];
		allMoon[0] = i;
		allMoon[1] = e;
		allMoon[2] = g;
		allMoon[3] = c;

		double iter = 1;
		applyGravityAndVelocity(allMoon);

		while (!(i.isTheOriginal() && c.isTheOriginal() && e.isTheOriginal() && g.isTheOriginal())) {
			if (iter % 100000000 == 0)
				System.out.println(new Date().toString() + " Iter=" + iter + "\n" + printMoons(allMoon));

			applyGravityAndVelocity(allMoon);
			iter++;
		}

		System.out.println("END! Iter=" + iter + "\n" + printMoons(allMoon));

		System.out.println("TotalEnergy=" + (i.getEnergy() + e.getEnergy() + c.getEnergy() + g.getEnergy()));
	}

	private static void applyGravityAndVelocity(Moon[] allMoon) {

		for (int idx = 0; idx < 4; idx++) {
			Moon curr = allMoon[idx];
			for (int idx2 = idx + 1; idx2 < 4; idx2++) {
				curr.applyGravity(allMoon[idx2]);
			}
		}

		for (int idx = 0; idx < 4; idx++) {
			allMoon[idx].applyVelocity();
		}
	}

	private static String printMoons(Moon[] allMoon) {
		StringBuilder sb = new StringBuilder();

		for (Moon moon : allMoon) {
			sb.append(moon.toString());
			sb.append("\n");
		}

		return sb.toString();
	}
}
