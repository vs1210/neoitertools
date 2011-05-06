package net.ericaro.neoitertools;

/**
 * generic interface to convert a type I (as In) to O (as Out)
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/Lambda">Lambda's
 *      wiki page</a>
 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 * 
 */
public interface Lambda<I, O> {

	public O map(I arg);

}
