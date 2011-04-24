package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Index;

public class EnumerateGenerator<T> implements Generator<Index<T>> {

	
	Generator<T> source;
	int i;
	
	public EnumerateGenerator(Generator<T> source) {
		this(source, 0);
	}
	
	public EnumerateGenerator(Generator<T> source, int startIndex) {
		super();
		this.source = source;
		i = startIndex;
	}


	public Index<T> next() throws NoSuchElementException {
		return new Index<T>(i++, source.next());
	}

	
	
}
