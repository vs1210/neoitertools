package net.ericaro.neoitertools.generators;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Yield;
import net.ericaro.neoitertools.YieldGenerator;

/** A {@link YieldGenerator} based on a {@link Yield} statement. Instead of implementing
 * the {@link Generator} protocol, it implements the more advanced {@link YieldGenerator} one.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/FullYieldGenerator">FullYieldGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class FullYieldGenerator<R,T> implements YieldGenerator<R,T>{

	
	private YieldThread<R, T> engine;

	
	
	public FullYieldGenerator(Yield<R, T> function ) {
		engine = new YieldThread<R, T>(this, function) ;
	}

	public T next(R u) {
		return engine.next(u);
	}
	
	
}
