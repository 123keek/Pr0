package de.uk.java.feader.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.uk.java.feader.data.Entry;
import de.uk.java.feader.data.Feed;
import de.uk.java.feader.io.FeedDownloader;

public class AppIO implements IAppIO {

	private FeedDownloader feedDownloader = new FeedDownloader();

	/**
	 * FEEDLIST LADEN
	 * 
	 * @param File
	 *         feedsFile
	 * @return List<Feed> Die Feeds werden als List<String> aus feedsFile
	 *         geladen reader.readLine() gibt die einzelnen Zeilen zurück, die
	 *         der Liste durch .add(line) hinzugefügt werden Die erstellte
	 *         List<String> wird durch .downloadFeeds(List<String>) von der
	 *         FeedDownloader Klasse in eine List<Feeds> gelegt und
	 *         zurückgegeben
	 */

	@Override
	public List<Feed> loadSubscribedFeeds(File feedsFile) {
		List<String> stringList = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(feedsFile));
			String line = reader.readLine();
			while (line != null) {
				stringList.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return feedDownloader.downloadFeeds(stringList);
	}

	/**
	 * FEEDLIST SPEICHERN
	 * 
	 * @param List<Feed>
	 * @param File
	 *            feedsFile Durch eine for-Schleife wird jedes Element der
	 *            List<Feed> durch writer.append() in die feedsFile geschrieben
	 */

	@Override
	public void saveSubscribedFeeds(List<Feed> feeds, File feedsFile) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(feedsFile, true));
			for (int i = 0; i < feeds.size(); i++) {
				writer.append(feeds.get(i).getUrl());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * CONFIG LADEN
	 * 
	 * @param File
	 *            configFile
	 * @return Properties Mithife einer while-Schleife werden die einzelnen
	 *         Lines der configFile, die durch den Reader segmentiert wurden,
	 *         einem String[] hinzugefügt. Bei Property-Objects ist drauf zu
	 *         achten, dass sie Key und Value enthalten; durch line.split("=")
	 *         und .setProperty() werden die einzelnen lines als Key und Value
	 *         in die Felder des Arrays geschrieben
	 */

	@Override
	public Properties loadConfig(File configFile) {
		Properties properties = new Properties();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(configFile));
			String line = reader.readLine();
			while (line != null) {
				if (line.indexOf("=") != -1) {
					String[] property = line.split("=");
					properties.setProperty(property[0], property[1]);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * CONFIG SPEICHERN
	 * 
	 * @param Properties
	 *            config
	 * @param File
	 *            Die Methode .foreach()->{} des Property-Objects
	 *            erstellt eine Schleife, die gemäß der Anzahl Key-Value Paare
	 *            in der config die .append() Methode des writers aufruft und diese durch ein "=" voneinander abgrenzt.
	 */

	@Override
	public void saveConfig(Properties config, File configFile) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile, true));
			config.stringPropertyNames().forEach((key) -> {
				try {
					writer.append(key + "=" + config.getProperty(key));
				} catch (IOException e) {
					e.printStackTrace();
				}

			});
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * HTML-EXPORT
	 * 
	 * @param List<Entry>
	 * @param File
	 *            Mithilfe einer for-Schleife werden die einzelnen
	 *            Objekte der list entries mithilfe der Methode .html() des
	 *            Entry-Objekts durch den writer.append in die htmlFile
	 *            geschrieben
	 */

	@Override
	public void exportAsHtml(List<Entry> entries, File htmlFile) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile, true));
			for (int i = 0; i < entries.size(); i++) {
				writer.append(entries.get(i).html());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
