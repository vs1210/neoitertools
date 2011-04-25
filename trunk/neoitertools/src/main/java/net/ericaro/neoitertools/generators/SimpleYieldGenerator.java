package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Yield;

public class SimpleYieldGenerator<T> implements Generator<T>{

	
	private YieldThread<?, T> engine;

	
	
	public SimpleYieldGenerator(Yield<Void, T> function ) {
		engine = new YieldThread<Void, T>(this, function) ;
	}

	public T next() throws NoSuchElementException {
		return engine.next(null);
	}
	
	
}
