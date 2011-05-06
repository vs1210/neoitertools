package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class RangeGeneratorTest {

	/*
	 * >>> range(10)
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
>>> range(1, 11)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
>>> range(0, 30, 5)
[0, 5, 10, 15, 20, 25]
>>> range(0, 10, 3)
[0, 3, 6, 9]
>>> range(0, -10, -1)
[0, -1, -2, -3, -4, -5, -6, -7, -8, -9]
>>> range(0)
[]
>>> range(1, 0)
[]
	 */
	
	@Test
	public void testRangeGeneratorIntInt() {
		RangeGenerator g = new RangeGenerator(1, 11);
		List<Integer> list = list(g);
		List<Integer> xlist = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		assert xlist.equals(list) : "range(1,11) failed";
	}


	@Test
	public void testRangeGeneratorInt() {
		RangeGenerator g = new RangeGenerator(10);
		List<Integer> list = list(g);
		List<Integer> xlist = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		assert xlist.equals(list) : "range(10) failed";
		
	}

	@Test
	public void testRangeGeneratorIntIntInt() {
		RangeGenerator g = new RangeGenerator(0, 30, 5);
		List<Integer> list = list(g);
		List<Integer> xlist = Arrays.asList(0, 5, 10, 15, 20, 25);
		assert xlist.equals(list) : "range(0,30,5) failed";
	}
	@Test
	public void testRangeGeneratorIntIntIntUncomplete() {
		RangeGenerator g = new RangeGenerator(0, 10, 3);
		List<Integer> list = list(g);
		List<Integer> xlist = Arrays.asList(0, 3, 6, 9);
		assert xlist.equals(list) : "range(0,10,3) failed";
	}
	@Test
	public void testRangeGeneratorIntIntIntNeg() {
		RangeGenerator g = new RangeGenerator(0, -10, -1);
		List<Integer> list = list(g);
		List<Integer> xlist = Arrays.asList(0, -1, -2, -3, -4, -5, -6, -7, -8, -9);
		assert xlist.equals(list) : "range(0,-10,-1) failed";
	}

}
