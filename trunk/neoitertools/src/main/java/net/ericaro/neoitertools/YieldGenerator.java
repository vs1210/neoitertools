package net.ericaro.neoitertools;

import java.util.NoSuchElementException;


/** A fully featured python generator with a both way communication between the yield function, and the generator.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/YieldGenerator">YieldGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public interface YieldGenerator<U,V>{

	
	
	public V next(U u) throws NoSuchElementException;
}
