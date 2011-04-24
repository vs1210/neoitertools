package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

public class EmptyGenerator<T> implements Generator<T> {

	// absurd, but trivial, and the real absurd is to fail to implement the trivial ;-)
	public T next() throws NoSuchElementException {
		throw new NoSuchElementException();
	}

	
	
	
}
