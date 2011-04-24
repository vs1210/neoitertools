package net.ericaro.neoitertools.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

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
