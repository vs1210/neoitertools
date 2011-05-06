package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;

/** Returns items from the generator iif the condition is true.
 * 
 *  Note that the condition passed can be negated.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/FilterGenerator">FilterGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class FilterGenerator<T> implements Generator<T> {

	private final Generator<T> source;
	private final Lambda<T,Boolean> predicate;
	private boolean negate;
	
	
	public FilterGenerator(Lambda<T,Boolean> predicate, Generator<T> source) {
		this(predicate, source, false);
	}
	
	public FilterGenerator(Lambda<T,Boolean> predicate, Generator<T> source, boolean negate) {
		super();
		this.predicate = predicate;
		this.source = source;
		this.negate = negate ;
	}



	public T next() throws NoSuchElementException {
		T next = source.next() ;
		while (negate ^ predicate.map(next) )
			next = source.next() ;
		return next;
	}

}
