package net.ericaro.neoitertools.generators.combinatorics;

import static net.ericaro.neoitertools.Itertools.*;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Itertools;

import org.junit.Test;

public class CombinationNumberTest {
	//combinations('ABCD', 2) --> AB AC AD BC BD CD
	//combinations(range(4), 3) --> 012 013 023 123

	@Test
	public void testCombinationNumber(){
		List<List<Integer>> xlist = Arrays.asList(   Arrays.asList(0,1,2),Arrays.asList( 0,1,3), Arrays.asList(0,2,3),Arrays.asList(1,2,3) )  ;
		
		List<List<Integer>> list = list(combinations(range(4), 3));
		
		System.out.println(xlist);
		System.out.println(list);
		
		assert xlist.equals(list);
	}

}
