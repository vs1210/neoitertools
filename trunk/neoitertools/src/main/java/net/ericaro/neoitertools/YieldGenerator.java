package net.ericaro.neoitertools;

import java.util.NoSuchElementException;


/** A fully featured python generator with a both way communication between the yield function, and the generator.
 * 
 * @author eric
 *
 * @param <U>
 * @param <V>
 */
public interface YieldGenerator<U,V>{

	
	
	public V next(U u) throws NoSuchElementException;
}
