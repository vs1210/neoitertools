package net.ericaro.neoitertools.generators;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

public class CharSequenceGenerator implements Generator<Character>{

	
	
	
	private CharSequence seq;
	private int i;

	public CharSequenceGenerator(CharSequence seq) {
		this.seq = seq;
		i= 0;
	}

	public Character next() throws NoSuchElementException {
		if (i>= seq.length()) throw new NoSuchElementException() ;
		return seq.charAt(i++);
	}

	
	
	
}
