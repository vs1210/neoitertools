package net.ericaro.neoitertools;

import java.util.NoSuchElementException;

/** When the sequence is exhausted the next method will throw a
 * {@link NoSuchElementException}.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/AboutGenerators">Python vs Java Iterator protocols</a>
 * @see <a
 *      href="http://code.google.com/p/neoitertools/wiki/Generator">Generator's
 *      wiki page</a>
 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public interface Generator<T> {

	/**
	 * 
	 * @return the next item in the sequence.
	 * @throws NoSuchElementException
	 *             when sequence is exhausted.
	 */
	public T next() throws NoSuchElementException;

}
