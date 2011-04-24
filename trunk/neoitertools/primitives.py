#!/usr/bin/env python
# -*- coding: utf-8 -*-

import string

iterator_template = string.Template("""package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;
import net.ericaro.neoitertools.Generator;
/** A simple Generator over a $type array
 * 
 * @author eric
 *
 */
public class ${Type}Generator implements Generator<${Type}> {

	
	private ${type}[] array;
	int index, end;
	public ${Type}Generator(${type}[] array) {
		this(array, 0, array.length);
	}
	
	public ${Type}Generator(${type}[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public ${Type} next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}""")

call_template=string.Template("""
/**
	 * Turns any ${type}[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<${Type}> iter(${type}[] array) {
		return new ${Type}Generator(array);
	}
""")


types = [
("byte"		, "Byte"      ),
("char"		, "Character" ),
("short"		, "Short" ),
("int"		, "Integer"   ),
("long"		, "Long"      ),
("float"		, "Float" ),
("double"	, "Double"    ),
("boolean"	, "Boolean"   ),
]

calls = []

for type, Type in types:
	calls.append( call_template.substitute(type=type, Type=Type) )
	with open('src/main/java/net/ericaro/neoitertools/generators/primitives/'+Type+'Generator.java', 'w') as f:
		print "generating ", Type
		print >>f, iterator_template.substitute(type=type, Type=Type)

for call in calls:
	print call





