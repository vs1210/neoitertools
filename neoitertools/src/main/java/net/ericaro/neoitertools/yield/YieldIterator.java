package net.ericaro.neoitertools.yield;

import java.util.Iterator;
import java.util.NoSuchElementException;


/** Yield statement support in neoitertools is based on two object; a YieldFunction to be implemented, and this iterator.
 * 
 * Simply creates a new instance of this YieldIterator with your implementation of the YieldFunction.
 * 
 * @author eric
 *
 */
public class YieldIterator<V> implements Iterator<V>{

	
	
	private YieldThread<Void, V> engine;
	private boolean hasNext= true;
	private V next;
	
	public YieldIterator(YieldFunction<V> function  ) {
		engine = new YieldThread<Void, V>(this, function) ;
		next();
	}

	public boolean hasNext() {
		return hasNext;
	}

	public V next() {
		V current = next;
		try{
			next = engine.next(null);
		}catch(NoSuchElementException e){
			hasNext = false;
		}
		return current;
	}

	public void remove() {throw new UnsupportedOperationException();	}

	
	
	
}
