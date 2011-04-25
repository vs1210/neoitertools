package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;

/** A {@link Generator} that apply a mapping {@link Lambda} function first.
 * 
 * @author eric
 *
 * @see <a href="http://code.google.com/p/neoitertools/wiki/MapGenerator">MapGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class MapGenerator<T,K> implements Generator<K> {

	Generator<T> source;
	Lambda<T,K> map;
	public MapGenerator(Lambda<T, K> map, Generator<T> source) {
		super();
		this.map = map;
		this.source = source;
	}
	public K next() throws NoSuchElementException {
		return map.map(source.next());
	}
	
	
	
	
}
