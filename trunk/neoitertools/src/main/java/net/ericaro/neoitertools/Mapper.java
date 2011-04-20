package net.ericaro.neoitertools;


/** generic interface to convert a type I (as In) to O (as Out)
 * 
 * @author eric
 *
 * @param <I>
 * @param <O>
 */
public interface Mapper<I, O> {

	
	public O map(I arg);
	
}
