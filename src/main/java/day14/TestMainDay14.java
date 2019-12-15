package day14;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TestMainDay14 {

	private static final String PUZZLE_INPUT = "simpleD14.txt";;

	public static void main(String[] args) throws IOException {
		Path path = FileSystems.getDefault().getPath(PUZZLE_INPUT);

		Map<String, Reaction> reactions = loadMapOfReaction(path);

		System.out.println(reactions);

	}

	private static Map<String, Reaction> loadMapOfReaction(Path path) throws IOException {
		Map<String, Reaction> reactions = new HashMap<>();

		Iterator<String> it = Files.lines(path, StandardCharsets.UTF_8).iterator();

		while (it.hasNext()) {
			String line = it.next();

			String[] sep = line.replace("=> ", "=>").split("=>");
			String[] result = sep[1].split(" ");
			Reaction curr = new Reaction(Integer.parseInt(result[0].trim()));

			String[] elements = sep[0].replace(", ", ",").split(",");
			for (int i = 0; i < elements.length; i++) {
				String[] token = elements[i].split(" ");
				curr.requisite.add(new Element(Integer.parseInt(token[0].trim()), token[1].trim()));
			}
			reactions.put(result[1].trim(), curr);
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

	public String toString() {
		return "<" + qty + " " + name + ">";
	}
}

class Reaction {
	int qtyProd;
	List<Element> requisite;

	public Reaction(int qtyProd) {
		this.qtyProd = qtyProd;
		requisite = new ArrayList<>();
	}

	public String toString() {
		return "Prod:" + qtyProd + " " + requisite;
	}
}