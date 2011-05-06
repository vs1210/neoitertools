package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/** A generator that returns nothing, never.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/EmptyGenerator">EmptyGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class EmptyGenerator<T> implements Generator<T> {

	// absurd, but trivial, and the real absurd is to fail to implement the trivial ;-)
	public T next() throws NoSuchElementException {
		throw new NoSuchElementException();
	}

	
	
	
}
