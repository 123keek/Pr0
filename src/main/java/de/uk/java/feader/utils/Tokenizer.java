package de.uk.java.feader.utils;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer implements ITokenizer {

	/**
	 * TOKENIZER 
	 * Ein StringBuilder dient zum Aneinanderreihen der g�ltigen
	 * Zeichen. Mithilfe einer for-Schleife wird der zu tokenisierende String an
	 * den einzelnen Stellen i mithilfe der.isLetter Methode des Character
	 * Objekts �berpr�ft. Dies f�hrt zu 3 M�glichkeiten: 1. Das Zeichen ist ein
	 * Buchstabe: in diesem Fall wird es term angef�gt 2. es ist kein Buchstabe
	 * und term ist leer: Dieses Zeichen kann also ignoriert werden 3. es ist
	 * kein Buchstabe und term ist nicht leer: In diesem Fall endet das Wort und
	 * term wird tokenized hinzugef�gt und resettet.
	 * 
	 * @param String
	 *            text
	 * @return tokenizedList
	 */
	@Override
	public List<String> tokenize(String text) {
		List<String> tokenized = new ArrayList<String>();
		StringBuilder term = new StringBuilder();

		for (int i = 1; i <= text.length(); i++) {
			if (Character.isLetter(text.charAt(i))) {
				term.append(text.charAt(i));
			} else if (term == null) {

			} else {
				tokenized.add(term.toString());
				term = new StringBuilder();

			}
		}
		return tokenized;
	}

}
