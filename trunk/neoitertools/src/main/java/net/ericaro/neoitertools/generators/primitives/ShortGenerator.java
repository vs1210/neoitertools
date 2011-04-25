package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
/** A simple Generator over a short array
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/ShortGenerator">ShortGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class ShortGenerator implements Generator<Short> {

	
	private short[] array;
	int index, end;
	public ShortGenerator(short[] array) {
		this(array, 0, array.length);
	}
	
	public ShortGenerator(short[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Short next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
