package net.ericaro.neoitertools.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Yield;
/** A {@link Generator} based on a {@link Yield} statement. It does not support return value from the yield statement so that it can support java standard {@link Iterator} protocol.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/AboutGenerators">About Python vs Java Iterator Protocols.</a>
 * @see <a href="http://code.google.com/p/neoitertools/wiki/SimpleYieldGenerator">SimpleYieldGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 *
 */
public class SimpleYieldGenerator<T> implements Generator<T>{

	
	private YieldThread<?, T> engine;

	
	
	public SimpleYieldGenerator(Yield<Void, T> function ) {
		engine = new YieldThread<Void, T>(this, function) ;
	}

	public T next() throws NoSuchElementException {
		return engine.next(null);
	}
	
	
}
