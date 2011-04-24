package net.ericaro.neoitertools.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;


/**
 * a Generator that returns elements from the Generator as long as the predicate
 * is true.
 * 
 * 
 * @author eric
 * 
 * @param <T>
 */
public class TakeWhileGenerator<T> implements Generator<T> {

	private Generator<T> iterator;
	private Lambda<T,Boolean> predicate;

	/**
	 * Make an iterator that returns elements from the iterator as long as the
	 * predicate is true.
	 * 
	 * @param iterator
	 * @param predicate
	 */
	public TakeWhileGenerator(Lambda<T, Boolean> predicate,Generator<T> iterator) {
		this.iterator = iterator;
		this.predicate = predicate;
	}


	public T next() {
			T next = iterator.next();
			if (! predicate.map(next) ) throw new NoSuchElementException() ; 
		return next;
	}
}
