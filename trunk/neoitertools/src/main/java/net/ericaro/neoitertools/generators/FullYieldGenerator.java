package net.ericaro.neoitertools.generators;

import net.ericaro.neoitertools.Yield;
import net.ericaro.neoitertools.YieldGenerator;

public class FullYieldGenerator<R,T> implements YieldGenerator<R,T>{

	
	private YieldThread<R, T> engine;

	
	
	public FullYieldGenerator(Yield<R, T> function ) {
		engine = new YieldThread<R, T>(this, function) ;
	}

	public T next(R u) {
		return engine.next(u);
	}
	
	
}
