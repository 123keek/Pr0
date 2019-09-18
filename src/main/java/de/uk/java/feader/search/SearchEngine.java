package de.uk.java.feader.search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import de.uk.java.feader.data.Entry;
import de.uk.java.feader.data.Feed;
import de.uk.java.feader.utils.ITokenizer;
import de.uk.java.feader.utils.Tokenizer;

public class SearchEngine implements ISearchEngine {

	/**
	 * Searches the search index for <code>searchTerm</code> and returns a
	 * <code>List</code> of <code>Entry</code> objects whose title or content text
	 * contains <code>searchTerm</code>. If no results are found in the search
	 * process, this method should return an empty list. If, on the other hand, the
	 * search index is empty (e.g. at first run of application), this method should
	 * return <code>null</code>.
	 * 
	 * @param searchTerm
	 * @return A list of search results (Entry instances), an empty list (if no
	 *         results), or <code>null</code> (if no index).
	 */

	/**
	 * 
	 * SEARCH
	 * Die Suchfunktion ist mithilfe einer while-Schleife und der .equals() Methode
	 * für Strings realisiert.
	 * 
	 */

	Map<String, List<Entry>> invIndex = new HashMap<String, List<Entry>>();

	@Override
	public List<Entry> search(String searchTerm) {
		List<Entry> result = new ArrayList<Entry>();
		Entry currentEntry;
		int counter = 0;
		if (invIndex == null) {
			return null;
		}
		// while-Schleife zur Iteration über den Index
		while (counter < invIndex.size()) {
			currentEntry = invIndex.get(searchTerm).get(counter);
			// Wenn der übergebene Term im aktuellen Entry steht, wird er result hinzugefügt
			if (searchTerm.equals(currentEntry.getContent())) {
				result.add(currentEntry);
			}
			counter++;
		}
		return result;
	}

	/**
	 * CREATE INDEX 
	 * Die verwendete Datenstruktur zur Realisierung des invertierten
	 * Index ist eine Map. Mittels einer for-Schleife wird über die Liste der
	 * Feed-Elemente iteriert, bei jedem einzelnen Feed wird die Methode
	 * addToSearchIndex aufgerufen. Innerhalb dieser werden die Feed Objekte
	 * weiterverarbeitet, tokenized und anschließend als key-value-Paar in den Index gelegt.
	 * 
	 * @param List<feeds>
	 */

	@Override
	public void createSearchIndex(List<Feed> feeds) {
		// iterate over single feeds to add(feed) to index
		for (int feedcount = 0; feedcount < feeds.size(); feedcount++) {
			Feed feed = feeds.get(feedcount);
			this.addToSearchIndex(feed);
		}
	}


	@Override
	public void addToSearchIndex(Feed feed) {
		Map<String, List<Entry>> invIndex = new HashMap<String, List<Entry>>();
		Tokenizer tokenizer = new Tokenizer();
		// iterate over single entry objects
		for (int entrycount = 0; entrycount < feed.getEntriesCount(); entrycount++) {

			// create List<Entry> to add to the index
			List<Entry> entryList = new ArrayList<Entry>();
			entryList.add(feed.getEntries().get(entrycount));

			// tokenize terms to add to the index (via List<String>)
			String text = feed.getEntries().get(entrycount).html();
			List<String> termList = tokenizer.tokenize(text);

			// iterate terms (in single entry) and get String term to add to the index
			for (int termCount = 0; termCount < termList.size(); termCount++) {
				String term = termList.get(termCount);
				// index term if that term is not already indexed
				if (!invIndex.containsKey(term)) {
					invIndex.put(term, entryList);
				}

			}
		}
	}

	/**
	 * Sets the ITokenizer instance used by this implementation of ISearchEngine
	 * 
	 * @param tokenizer The ITokenizer instance to use
	 */
	@Override
	public void setTokenizer(ITokenizer tokenizer) {
		// TODO Auto-generated method stub
	}

	/**
	 * INDEX SPEICHERN
	 * Ähnlich wie bei den anderen Input/Output Funktionen ermöglicht ein writer das
	 * externe Speichern der Daten. "=" Zeichen grenzen in den einzelnen Lines die 
	 * Keys von den Values ab.
	 * 
	 * 
	 */

	@Override
	public void saveSearchIndex(File indexFile) {
		if (this.indexExists() == true) {
			Map<String, List<Entry>> invIndex = new HashMap<String, List<Entry>>();
			try {
				// create writer with indexFile to store the data to
				BufferedWriter writer = new BufferedWriter(new FileWriter(indexFile, true));
				//for each indexed String build a StringBuilder, that will compress the List<Entry>
				//objects to regular String that can be saved to a file
				invIndex.keySet().forEach((key) -> {
					try {
						StringBuilder value = new StringBuilder();
						// iterate over every Entry & save it in temp via .html function
						for (int entryCount = 0; entryCount < invIndex.get(key).size(); entryCount++) {
							String temp = invIndex.get(key).get(entryCount).html();
							value.append(temp);
						}
						//write single key-value pair to the file
						writer.append(key + "=" + value);
					} catch (IOException e) {
						e.printStackTrace();
					}

				});

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Loads a previously saved search index structure from the given file <b>IF</b>
	 * the file exists. Otherwise, the index structure should remain uninitialized
	 * and calls to <code>search()</code> should return <code>null</code>. In this
	 * case, the application will call <code>createSearchIndex()</code> to
	 * initialize a new index.
	 * 
	 * @param indexFile
	 */


	@Override
	public void loadSearchIndex(File indexFile) {
		BufferedReader reader;
		Tokenizer tokenizer = new Tokenizer();
		if(indexFile.exists()) {
			try {
				reader = new BufferedReader(new FileReader(indexFile));
				String line = reader.readLine();
				while (line != null) {
					
					if (line.indexOf("=") != -1) {
						String[] temp = line.split("=");
						String tok = temp[1];
					}
					
				}
				
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Returns <code>true</code> if a search index exists for this ISearchEngine
	 * instance, <code>false</code> otherwise.
	 * 
	 * @return
	 */

	@Override
	public boolean indexExists() {
		if (invIndex == null) {
			return false;
		} else {
			return true;
		}
	}
}
