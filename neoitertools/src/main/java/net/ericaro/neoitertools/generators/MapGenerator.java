package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;

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
