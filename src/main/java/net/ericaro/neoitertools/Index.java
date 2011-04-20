package net.ericaro.neoitertools;

/** A simple immutable object used to handle both the index and the object
 * @author eric
 *
 * @param <T>
 */
public class Index<T> {

	public final int i;
	public final T value;
	
	public Index(int i, T value) {
		this.i = i;
		this.value = value;
	}

	
	
	
}
