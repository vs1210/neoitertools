package net.ericaro.neoitertools.iterators;

import java.util.Iterator;


/** Turn any CharSequence into an Iterator of Character.
 * 
 * @author eric
 *
 */
public class CharSequenceIterator implements
		Iterator<Character> {
	private final CharSequence seq;
	int i = 0;

	public CharSequenceIterator(CharSequence seq) {
		this.seq = seq;
	}

	public boolean hasNext() {
		return i < seq.length();
	}

	public Character next() {
		return seq.charAt(i++);
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}