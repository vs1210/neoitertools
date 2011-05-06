package net.ericaro.neoitertools.generators.combinatorics;

import static net.ericaro.neoitertools.Itertools.in;

import java.util.Arrays;

import org.junit.Test;

public class CombinatorialNumberTest {

	@Test
	public void testInc() {
		for (int[] indices: in(new FixedSumNumber(5, 3)))
			System.out.println(Arrays.toString(indices));

	}
	@Test
	public void testInc2() {
		for (int[] indices: in(new FactorialNumber( 4)))
			System.out.println(Arrays.toString(indices));
		
	}

}
