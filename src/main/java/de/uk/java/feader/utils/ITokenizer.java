package de.uk.java.feader.utils;

import java.util.List;

public interface ITokenizer {
	
	/**
	 * Tokenizes the input text into a <code>List</code>
	 * of <code>String</code> objects representing the
	 * single tokens of the input text.
	 * A token is supposed to be a single word of the input text, not
	 * containing anything but language letter characters, so that
	 * <code>"Feader is called Feader, right?"</code> becomes
	 * a <code>List</code> like <code>[Feader, is, called, Feader, right]</code>.
	 * <b>Also, the tokenizer is supposed to ignore HTML tags and only process text contents,
	 * so that "&lt;h1&gt;This is a heading!!!&lt;/h1&gt;" is tokenized to
	 * "[This, is, a, heading]" !</b>
	 * @param text The text to tokenize
	 * @return The <code>List</code> containing the tokens as strings
	 */
	public List<String> tokenize(String text);

}

/**
 * 
 * package de.uni_koeln.spinfo.textengineering.ir.boole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.uni_koeln.spinfo.textengineering.ir.basic.Corpus;
import de.uni_koeln.spinfo.textengineering.ir.basic.Searcher;
import de.uni_koeln.spinfo.textengineering.ir.basic.Work;

public class InvertedIndex implements Searcher {

	Map<String, SortedSet<Integer>> index;// Unsere Zugriffsstruktur

	public InvertedIndex(Corpus corpus) {
		Long start = System.currentTimeMillis();
		index = index(corpus);
		System.out.println("Indexierung in: " + (System.currentTimeMillis() - start) + " ms.");
	}

	private Map<String, SortedSet<Integer>> index(Corpus corpus) {
		Map<String, SortedSet<Integer>> invIndex = new HashMap<String, SortedSet<Integer>>();
		// Der Index wird aufgebaut, indem ...
		List<Work> works = corpus.getWorks();
		// ... für jedes Werk ...
		for (int i = 0; i < works.size(); i++) {
			// ... Text tokenisiert wird ...
			Work work = works.get(i);
			List<String> terms = Arrays.asList(work.getText().split("\\P{L}+"));
			Collections.sort(terms);// (das hier muss nicht unbedingt sein, machen wir nur, um konform zu den Folien zu
									// bleiben...)
			// ... um dann für jeden Term ...
			for (String t : terms) {
				// ... die postingsList aus dem Index zu holen.
				SortedSet<Integer> postingsList = invIndex.get(t);
				/*
				 * Beim ersten Vorkommen des Terms ist diese noch leer (null), also legen wir uns einfach eine neue an:
				 
				if (postingsList == null) {
					postingsList = new TreeSet<Integer>();
					invIndex.put(t, postingsList);
				}
				/*
				 * Der Term wird indexiert, indem die Id des aktuellen Werks (= der aktuelle Zählerwert) der
				 * postings-list hinzugefügt wird:
				 
				postingsList.add(i);
			}
		}
		return invIndex;
	}

	@Override
	public Set<Integer> search(String query) {
		// Suchen im Index
		long start = System.currentTimeMillis();
		Set<Integer> result = new HashSet<Integer>();
		List<String> queries = Arrays.asList(query.split("\\P{L}+"));

		// erstmal für jede Teilquery das Zwischenergebnis sammeln:
		List<Set<Integer>> postingsLists = new ArrayList<Set<Integer>>();
		for (String q : queries) {
			Set<Integer> zwischenergebnis = index.get(q);
			postingsLists.add(zwischenergebnis);
		}
		// Ergebnis ist die Schnittmenge (Intersection) der ersten Liste...
		result = postingsLists.get(0);
		// ... mit allen weiteren:
		for (Set<Integer> pl : postingsLists) {
			result.retainAll(pl);// AND-Verknüpfung
			// result.addAll(pl);// OR-Verknüpfung
		}
		System.out.println("Suchdauer: " + (System.currentTimeMillis() - start) + " ms.");
		return result;
	}

}

 * 
 * 
 */
