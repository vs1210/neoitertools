package net.ericaro.neoitertools;


/** generic operator definition within T domain.
 * 
 * @author eric
 *
 * @see <a href="http://code.google.com/p/neoitertools/wiki/Operator">Operator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public interface Operator<T> {

	
	public T operate(T t1, T t2);
	
}
