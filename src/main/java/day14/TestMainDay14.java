package day14;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TestMainDay14 {

	private static final String PUZZLE_INPUT = "simpleD14.txt";;

	public static void main(String[] args) throws IOException {
		Path path = FileSystems.getDefault().getPath(PUZZLE_INPUT);

		Map<String, List<Element>> asteroidsBelt = loadMapOfReaction(path);

	}

	private static Map<String, List<Element>> loadMapOfReaction(Path path) throws IOException {
		Map<String, List<Element>> reactions = new HashMap<>();

		Iterator<String> it = Files.lines(path, StandardCharsets.UTF_8).iterator();

		while (it.hasNext()) {
			String line = it.next();

			String[] sep = line.split("=>");
		}

		return reactions;
	}
}

class Element {
	int qty;
	String name;

	public Element(int qty, String name) {
		this.qty = qty;
		this.name = name;
	}
}