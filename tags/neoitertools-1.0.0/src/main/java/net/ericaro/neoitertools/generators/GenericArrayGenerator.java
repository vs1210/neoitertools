package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/** A {@link Generator} based on any Object Type array.
 * 
 * @author eric
 *
 * @see <a href="http://code.google.com/p/neoitertools/wiki/GenericArrayGenerator">GenericArrayGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class GenericArrayGenerator<T> implements Generator<T> {

	private T[] values;
	private int i;

	public GenericArrayGenerator(T... values) {
		super();
		this.values = values;
		i = 0;
	}

	public T next() throws NoSuchElementException {
		if (i>= values.length) throw new NoSuchElementException();
		return values[i++];
	}
	
	

	
	
}
