package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;


/** A Generator of Character read from any {@link CharSequence}. 
 * There are many {@link CharSequence}s in Java: {@link String}, {@link StringBuilder}, {@link StringBuffer} ...
 *  
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/CharSequenceGenerator">CharSequenceGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class CharSequenceGenerator implements Generator<Character>{
	
	private CharSequence seq;
	private int i;

	public CharSequenceGenerator(CharSequence seq) {
		this.seq = seq;
		i= 0;
	}

	public Character next() throws NoSuchElementException {
		if (i>= seq.length()) throw new NoSuchElementException() ;
		return seq.charAt(i++);
	}

	
	
	
}
