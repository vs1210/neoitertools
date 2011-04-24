package net.ericaro.neoitertools;


/** generic interface to convert a type I (as In) to O (as Out)
 * 
 * @author eric
 *
 * @param <I>
 * @param <O>
 */
public interface Lambda<I, O> {

	
	public O map(I arg);
	
}
