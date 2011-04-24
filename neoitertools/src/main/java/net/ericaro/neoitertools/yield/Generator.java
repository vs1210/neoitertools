package net.ericaro.neoitertools.yield;

public class Generator<U,V> {

	
	
	private YieldThread<U, V> engine;

	public static <U,V> Generator<U,V> iter(Generable<U, V> function  ){
		return new Generator<U,V>(function);
	}
	
	Generator(Generable<U, V> function ) {
		engine = new YieldThread<U, V>(this, function) ;
	}

	public V next(U u){
		return engine.next(u);
	}
}
