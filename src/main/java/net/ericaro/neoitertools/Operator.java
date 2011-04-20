package net.ericaro.neoitertools;


/** generic operator definition with T domain.
 * 
 * @author eric
 *
 * @param <T>
 */
public interface Operator<T> {

	
	public T operate(T t1, T t2);
	
}
