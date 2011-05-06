package net.ericaro.neoitertools.generators.combinatorics;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class SubListNumberTest {

	@Test
	public void testSubListNumber() {
		// "sublist(range(3), 2) --> 01 10 02 20 12 21"
		List<int[]> xlist = Arrays.asList(   
				new int[] { 0,1},
				new int[] { 1,0},
				new int[] { 0,2},
				new int[] { 2,0},
				
				new int[] { 1,2},
				new int[] { 2,1}
				)  ;
				

				
				for(int[] c : xlist)
					System.out.print(Arrays.toString(c)+" , ");
				System.out.println();
				
				SubListNumber fn = new SubListNumber(3, 2) ;
				
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
