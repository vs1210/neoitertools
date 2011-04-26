package net.ericaro.neoitertools.generators.combinatorics;

import static net.ericaro.neoitertools.Itertools.combinations;
import static net.ericaro.neoitertools.Itertools.list;
import static net.ericaro.neoitertools.Itertools.range;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class FactorialNumberTest {

	@Test
	public void testFactorialNumber() {
		
List<int[]> xlist = Arrays.asList(   
		new int[] { 0,0,0},
		new int[] { 0,1,0}, 
		new int[] { 1,0,0}, 
		new int[] { 1,1,0}, 
		new int[] { 2,0,0}, 
		new int[] { 2,1,0} 
		)  ;
		

		
		for(int[] c : xlist)
			System.out.print(Arrays.toString(c)+" , ");
		System.out.println();
		
		FactorialNumber fn = new FactorialNumber(3) ;
		
		try {
			int i =0;
			while(true){
				int[] next = fn.next();
				assert Arrays.equals(xlist.get(i++), next) : "oops "+i+"th element falied"; 
			}
		} catch (NoSuchElementException e) {	}
	}

}
