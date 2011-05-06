package net.ericaro.neoitertools.generators.combinatorics;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CombinatoricsTest {

	@Test
	public void testApply() {
		
		List<Character> src = Arrays.asList('a','b', 'c');
		assert Arrays.asList('a','c').equals(Combinatorics.apply(src, new int[]{0,2}));
		assert Arrays.asList('c','b', 'a').equals(Combinatorics.apply(src, new int[]{2,1,0}));
		assert Arrays.asList('c').equals(Combinatorics.apply(src, new int[]{2}));
	}

	@Test
	public void testSelect() {
		List<Character> xlist = Arrays.asList('a','c');
		List<List<Character>> src = Arrays.asList( 
				Arrays.asList('a','b', 'c'), 
				Arrays.asList('1','2', '3', '4'), 
				Arrays.asList('_', '-', ',') 
				);
		assert Arrays.asList('a','3').equals(Combinatorics.select(src, new int[]{0,2}));
		assert Arrays.asList('a','3', '_').equals(Combinatorics.select(src, new int[]{0,2, 0}));
		assert Arrays.asList('c','4', ',').equals(Combinatorics.select(src, new int[]{2,3, 2}));
		assert Arrays.asList('c','4', ',','a','2', '-').equals(Combinatorics.select(src, new int[]{2,3, 2, 0, 1, 1}));
		
	}
}
