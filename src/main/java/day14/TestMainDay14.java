package day14;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class TestMainDay14 {

	private static final String PUZZLE_INPUT = "verySimpleD14.txt";
	private static final String FUEL = "FUEL";
	private static final String ORE = "ORE";

	public static void main(String[] args) throws IOException {
		Path path = FileSystems.getDefault().getPath(PUZZLE_INPUT);

		Map<String, Reaction> reactions = loadMapOfReaction(path);

		System.out.println("Reaction lenght: " + reactions.size());

		Reaction fuelRec = reactions.get(FUEL);

		Optional<Pair> elToSub = onlyOREReaction(fuelRec, reactions);
		while (elToSub.isPresent()) {
			System.out.println("Element sub: " + elToSub + " ORE requied till now:" + calcORERequired(fuelRec)
					+ "\n Res=" + fuelRec);
			subEl(fuelRec, elToSub.get(), reactions);
			elToSub = onlyOREReaction(fuelRec, reactions);
		}

		System.out.println("Final FUEL Res=" + fuelRec);

		int totOre = calcORERequired(fuelRec);

		System.out.println("Total ore required: " + totOre);
	}

	private static int calcORERequired(Reaction fuelRec) {
		int totOre = 0;
		for (Integer ore : fuelRec.requisite.values()) {
			totOre += ore;
		}

		return totOre;
	}

	private static void subEl(Reaction fuelRec, Pair element, Map<String, Reaction> reactions) {
		Reaction r = reactions.get(element.el);
		Double ratio = Math.ceil((double) element.qty / r.qtyProd);

		fuelRec.requisite.remove(element.el);

		for (int i = 0; i < ratio.intValue(); i++) {
			for (Entry<String, Integer> elReq : r.requisite.entrySet()) {
				Integer newQty = fuelRec.requisite.get(elReq.getKey());
				if (newQty != null)
					newQty += elReq.getValue();
				else
					newQty = elReq.getValue();

				fuelRec.requisite.put(elReq.getKey(), newQty);
			}
		}
	}

	private static Optional<Pair> onlyOREReaction(Reaction fuelRec, Map<String, Reaction> reactions) {
		List<Pair> toCheck = new ArrayList<>();
		for (Entry<String, Integer> element : fuelRec.requisite.entrySet()) {
			if (!element.getKey().equals(ORE)) {
				toCheck.add(new Pair(element.getKey(), element.getValue()));
			}
		}

		if (toCheck.isEmpty())
			return Optional.empty();

		for (Pair pair : toCheck) {
			Reaction rToCheck = reactions.get(pair.el);
			for (String elName : rToCheck.requisite.keySet()) {
				if (!elName.equals(ORE))
					return Optional.of(pair);
			}
		}

		return Optional.of(toCheck.get(0));
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
				curr.requisite.put(token[1].trim(), Integer.parseInt(token[0].trim()));
			}
			reactions.put(result[1].trim(), curr);
		}

		return reactions;
	}
}

class Pair {
	String el;
	Integer qty;

	public Pair(String el, Integer qty) {
		this.el = el;
		this.qty = qty;
	}

	public String toString() {
		return "<" + el + "," + qty + ">";
	}
}

class Reaction {
	int qtyProd;
	Map<String, Integer> requisite;

	public Reaction(int qtyProd) {
		this.qtyProd = qtyProd;
		requisite = new HashMap<>();
	}

	public String toString() {
		return "Prod:" + qtyProd + " " + requisite;
	}
}