package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

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
