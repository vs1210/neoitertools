package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;
import static net.ericaro.neoitertools.Itertools.range;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Lambda;

import org.junit.Test;

public class FilterGeneratorTest {

	// # ifilter(lambda x: x%2, range(10)) --> 1 3 5 7 9
	// # ifilterfalse(lambda x: x%2, range(10)) --> 0 2 4 6 8
	@Test
	public void testFilterGenerator() {

		List<Integer> xlist = Arrays.asList(1, 3, 5, 7, 9);
		List<Integer> list = list(new FilterGenerator<Integer>(new Lambda<Integer,Boolean>() {

			public Boolean map(Integer arg) {
				return arg%2 != 1;
			}
		}, range(10)));
		
		assert xlist.equals(list);
	}
	@Test
	public void testFilterGeneratorNegate() {
		
		List<Integer> xlist = Arrays.asList(0,2,4,6,8);
		List<Integer> list = list(new FilterGenerator<Integer>(new Lambda<Integer,Boolean>() {
			
			public Boolean map(Integer arg) {
				return arg%2 != 1;
			}
		}, range(10), true));
		
		assert xlist.equals(list);
	}

}
