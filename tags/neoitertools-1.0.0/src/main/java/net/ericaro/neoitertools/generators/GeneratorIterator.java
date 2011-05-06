package net.ericaro.neoitertools.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/** Gateway between the {@link Generator} world, and the Java {@link Iterator} one.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/GeneratorIterator">GeneratorIterator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
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
