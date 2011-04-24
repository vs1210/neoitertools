package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;
import static net.ericaro.neoitertools.Itertools.map;
import static net.ericaro.neoitertools.Itertools.range;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Lambda;

import org.junit.Test;

public class MapGeneratorTest {

	@Test
	public void testMapGenerator() {
		List<Integer> xlist = Arrays.asList(0,1,4,9,16);
		List<Integer> list = list( map(new Lambda<Integer, Integer>(){

			public Integer map(Integer arg) {
				return arg*arg;
			}}, range(5)));
		assert xlist.equals(list);
	}

}
