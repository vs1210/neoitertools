package net.ericaro.neoitertools;


/** A predicate turns a value into true or false. It's a specialized form of Mapper
 * 
 * @author eric
 *
 * @param <T>
 */
public interface Predicate<T> extends Mapper<T,Boolean> {
}
