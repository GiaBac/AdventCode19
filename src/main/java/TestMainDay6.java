import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TestMainDay6 {

	private static final String PUZZLE_INPUT = "puzzleInput.txt";
	private static final String SIMPLE_INPUT = "simpleInput.txt";

	private static final String YOU_P_NAME = "YOU";
	private static final String SANTA_P_NAME = "SAN";

	public static void main(String[] args) throws IOException {

		Path path = FileSystems.getDefault().getPath(PUZZLE_INPUT);

		Map<String, String> universe = new HashMap<>();

		Files.lines(path, StandardCharsets.UTF_8).forEach(l -> processOrbit(l, universe));

//		printTheUniverse(universe);
//		countOrbits(universe);

		Optional<Integer> numTransfer = calcNumTransfer(YOU_P_NAME, SANTA_P_NAME, universe);
		if (!numTransfer.isPresent()) {
			System.out.println("No path between me and santa!");
			return;
		}

		System.out.println("MCO = " + numTransfer.get());

	}

	private static Optional<Integer> calcNumTransfer(String p1, String p2, Map<String, String> universe) {

		Set<String> common = calcCommonPlanets(p1, p2, universe);

		System.out.println("Common Planet: " + common);

		int maxDist = Integer.MIN_VALUE;
		String mcoPlanet = "";

		for (String commonPlan : common) {
			int distFromCOM = calcPathToPlanet(commonPlan, "COM", universe).size();

			if (distFromCOM > maxDist) {
				maxDist = distFromCOM;
				mcoPlanet = commonPlan;
			}
		}

		System.out.println("MCO Planet: " + mcoPlanet);

		Set<String> pathFromPlanet1 = calcPathToPlanet(p1, mcoPlanet, universe);
		int distP1FromMCO = pathFromPlanet1.size();
		Set<String> pathFromPlanet2 = calcPathToPlanet(p2, mcoPlanet, universe);
		int distP2FromMCO = pathFromPlanet2.size();

		System.out.println("pathFromPlanet1: " + pathFromPlanet1);
		System.out.println("pathFromPlanet2: " + pathFromPlanet2);

		return (maxDist == Integer.MAX_VALUE) ? Optional.empty() : Optional.of(distP1FromMCO + distP2FromMCO - 2);
	}

	private static Set<String> calcCommonPlanets(String p1, String p2, Map<String, String> universe) {

		Set<String> ancestorsP1 = calcPathToPlanet(p1, "COM", universe);
		Set<String> ancestorsP2 = calcPathToPlanet(p2, "COM", universe);

		Set<String> intersect = new HashSet<>();
		for (String p1Parent : ancestorsP1) {
			if (ancestorsP2.contains(p1Parent))
				intersect.add(p1Parent);
		}

		return intersect;
	}

	private static Set<String> calcPathToPlanet(String p1, String target, Map<String, String> universe) {
		Set<String> path = new HashSet<>();
		String curr = p1;

		while (curr != null && !curr.equals(target)) {
			path.add(curr);
			curr = universe.get(curr);
		}

		return path;
	}

	private static void countOrbits(Map<String, String> universe) {
		int total = 0;

		for (String planet : universe.keySet()) {
			total += countPlanet(planet, universe);
		}

		System.out.println("Total = " + total);
	}

	private static int countPlanet(String planet, Map<String, String> universe) {
		String orbit = universe.get(planet);

		if (orbit == null)
			return 0;

		return countPlanet(orbit, universe) + 1;
	}

	private static void printTheUniverse(Map<String, List<String>> universe) {
		universe.forEach((p, l) -> System.out.println(l.toString() + "->" + p));
	}

	private static Object processOrbit(String l, Map<String, String> universe) {
		String[] split = l.split("\\)");

		assert split.length == 2;

		universe.put(split[1], split[0]);

		return l;
	}
}
