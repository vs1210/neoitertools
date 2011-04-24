package net.ericaro.neoitertools.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

public class GeneratorIterator<T> implements Iterator<T> {

	Generator<T> source;
	boolean hasNext ;
	T next;
	
	public GeneratorIterator(Generator<T> source) {
		super();
		this.source = source;
		hasNext = true;
		innerNext();
	}

	
	
	private void innerNext() {
		
		try {
			next = source.next();
		} catch (NoSuchElementException e) {
			hasNext = false;
		}
	}



	public boolean hasNext() {
		return hasNext;
	}

	
	
	public T next() {
		try{
			return next;
		}
		finally{
			innerNext() ;
		}
	}

	public void remove() {throw new UnsupportedOperationException();}

}
