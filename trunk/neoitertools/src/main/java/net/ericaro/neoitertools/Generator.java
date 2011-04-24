package net.ericaro.neoitertools;

import java.util.NoSuchElementException;

/** interface used to fit the generator concept of python.
 * 
 * It differs from the Iterator in that it has no "hasNext" method.
 * 
 * On the contrary, when the list is over the next method will throw a {@link NoSuchElementException}.
 * 
 * @author eric
 *
 * @param <T>
 */
public interface Generator<T> {

	/**
	 * 
	 * @return the next item in the Generator.
	 * @throws NoSuchElementException when the generator is empty. 
	 */
	public T next() throws NoSuchElementException;
}
