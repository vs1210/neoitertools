package net.ericaro.neoitertools.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/** A {@link Generator} using java {@link Iterator} as source.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/IteratorGenerator">IteratorGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class IteratorGenerator<T> implements Generator<T> {


	Iterator<T> iterator;

	public IteratorGenerator(Iterator<T> iterator) {
		super();
		this.iterator = iterator;
	}

	public T next() throws NoSuchElementException {
		if (!iterator.hasNext()) throw new NoSuchElementException() ; // that's an extra precaution, as iterator are expected to be consistent
		return iterator.next();
	}
	
	
	
	
	
	
	
	
	
}
