package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;

/** Drops item while the condition is true, and then start to return them. 
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/DropWhileGenerator">DropWhileGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 *
 */
public class DropWhileGenerator<T> implements Generator<T>{
	
	Generator<T> source;
	boolean useNextField = false;
	private T next;
	public DropWhileGenerator(Lambda<T,Boolean> predicate, Generator<T> source) {
		super();
		this.source = source;
		try {
			T next = source.next();
			while (predicate.map(next) )
				next = source.next();
			this. next = next; // the first that should not be drop 
			useNextField = true;
		} catch (NoSuchElementException e) {}
		
	}

	public T next() throws NoSuchElementException {
		if (useNextField ){
			T t = next;
			next = null;
			useNextField = false;
			return t;
		}
		else return source.next() ;
	}
	
	

}
