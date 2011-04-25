package net.ericaro.neoitertools;


/** Interface to implement the generator function with <a href="http://code.google.com/p/neoitertools/wiki/YieldStatement">Yield statement</a>.
 * 
 * @author eric
 
 * @see <a href="http://code.google.com/p/neoitertools/wiki/Yield">Yield's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 *
 */
public interface Yield<R,V> {

	
	/** While this method is running and making calls to the {@link Itertools#yield(Object)} statement, the associated
	 * generator can be used to get the yielded values.
	 * 
	 */
	public void generate();
}
