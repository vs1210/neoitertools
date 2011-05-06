package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/**
 * A generator that returns elements from the first generators until it is
 * exhausted, then proceeds to the next generator, until all of the generators are
 * exhausted. 
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/ChainGenerator">ChainGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 * 
 */
public class ChainGenerator<T> implements Generator<T> {
	Generator<Generator<T>> metaIterator;
	Generator<T> currentIterator;
	Generator<T> previousIterator;

	/** chain together a sequence of sequences.
	 * 
	 * @param iterators
	 */
	public ChainGenerator(Generator<Generator<T>> iterators) {
		metaIterator = iterators;
	}


	public T next() {
		if (currentIterator == null)
			currentIterator = metaIterator.next();
		// move to the next iterator
		try{
			return currentIterator.next();
		}catch(NoSuchElementException e){
			currentIterator = metaIterator.next();
			return next();
		}
	}
}