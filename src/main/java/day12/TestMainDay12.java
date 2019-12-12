package day12;

import java.util.Date;

public class TestMainDay12 {

	public static void main(String[] args) {
		Moon i = new Moon(-8, -18, 6, "Io");
		Moon e = new Moon(-11, -14, 4, "Europa");
		Moon g = new Moon(8, -3, -10, "Ganimede");
		Moon c = new Moon(-2, -16, 1, "Calliope");

		// Moon i = new Moon(-1, 0, 2);
		// Moon e = new Moon(2, -10, -7);
		// Moon g = new Moon(4, -8, 8);
		// Moon c = new Moon(3, 5, -1);

		Moon[] allMoon = new Moon[4];
		allMoon[0] = i;
		allMoon[1] = e;
		allMoon[2] = g;
		allMoon[3] = c;

		double iter = 1;
		applyGravityAndVelocity(allMoon);

		while (!(i.isTheOriginal(iter) && c.isTheOriginal(iter) && e.isTheOriginal(iter) && g.isTheOriginal(iter))) {
			if (iter % 300000000 == 0)
				System.out.println(new Date().toString() + " Iter=" + iter + "\n" + printMoons(allMoon));

			if (i.isTheOriginalOnX() && e.isTheOriginalOnX() && g.isTheOriginalOnX() && c.isTheOriginalOnX())
				System.out.println("All original on X at " + iter);
			if (i.isTheOriginalOnY() && e.isTheOriginalOnY() && g.isTheOriginalOnY() && c.isTheOriginalOnY())
				System.out.println("All original on Y at " + iter);
			if (i.isTheOriginalOnZ() && e.isTheOriginalOnZ() && g.isTheOriginalOnZ() && c.isTheOriginalOnZ())
				System.out.println("All original on Z at " + iter);

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
