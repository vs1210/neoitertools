package net.ericaro.neoitertools;

public interface Yield<R,V> {

	
	/** While this method is running and making calls to the {@link Itertools#yield(Object)} statement, the associated
	 * generator can be used to get the yielded values.
	 * 
	 */
	public void generate();
}
