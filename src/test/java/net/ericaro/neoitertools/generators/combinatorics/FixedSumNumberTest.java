package net.ericaro.neoitertools.generators.combinatorics;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class FixedSumNumberTest {

	@Test
	public void testFixedSumNumber() {
		
		List<int[]> xlist = Arrays.asList(   
				new int[] { 0,0,0},
				new int[] { 0,0,1},
				new int[] { 0,0,2},
				new int[] { 0,1,0},
				new int[] { 0,1,1},
				new int[] { 0,2,0},
				new int[] { 1,0,0},
				new int[] { 1,0,1},
				new int[] { 1,1,0},
				new int[] { 2,0,0}
				)  ;
				

				
				for(int[] c : xlist)
					System.out.print(Arrays.toString(c)+" , ");
				System.out.println();
				
				FixedSumNumber fn = new FixedSumNumber(5,3) ;
				
				int i =0;
				try {
					while(true){
						int[] next = fn.next();
						System.out.print(Arrays.toString(next)+" , ");
						assert Arrays.equals(xlist.get(i++), next) : "oops "+i+"th element falied"; 
					}
				} catch (NoSuchElementException e) {	}
				assert i == xlist.size();
			}

}
