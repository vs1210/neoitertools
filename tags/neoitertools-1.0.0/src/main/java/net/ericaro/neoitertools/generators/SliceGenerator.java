package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/**
 * a Generator that returns selected elements from the Generator. If start is
 * non-zero, then elements from the Generator are skipped until start is reached.
 * Afterward, elements are returned consecutively unless step is set higher than
 * one which results in items being skipped. It stops at the specified position.
 * slice() does not support negative values for start, stop, or step.
 * 
 * @author eric
 * 
 * @see <a href="http://code.google.com/p/neoitertools/wiki/SliceGenerator">SliceGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class SliceGenerator<T> implements Generator<T> {
	private final int step;
	private final Generator<T> source;
	private final int stop;
	int i;
	T next;

	public SliceGenerator(Generator<T> sequence, int start, int stop, int step) {
		this.source = sequence;
		this.step = step;
		this.stop = stop;
		i = 0;
		try {
			while (i < start ) {
				sequence.next();
				i++;
			}
		} catch (NoSuchElementException e) {}
	}

	public T next() {
		if (i >= stop ) throw new NoSuchElementException() ;
		
		T current = source.next();
		// now move to the next -1 one
		int k = i;
		i++;
		
		try {
			while (i < k + step && i < stop ) { // consume has
												// much as
				// possible
				i++;
				next = source.next();// consume item
			}
		} catch (NoSuchElementException e) {
			i = stop;// to make sure I will stop
		} // ignored as I was trying to move to the next item
		return current;

	}
}