#!/usr/bin/env python
# -*- coding: utf-8 -*-

import string

iterator_template = string.Template("""package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a $Type array
 * 
 * @author eric
 *
 */
public class ${Type}Iterator extends PrimitiveIterator<${Type}> {

	
	
	
	private ${type}[] array;
	public ${Type}Iterator(${type}[] array) {
		this(array, 0, array.length);
	}
	
	public ${Type}Iterator(${type}[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected ${Type} get(int j) {
		return array[j];
	}

}""")

call_template=string.Template("""
/**
	 * Turns any ${type}[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<${Type}> iter(${type}[] array) {
		return new ${Type}Iterator(array);

	}
""")

iterable_template=string.Template("""
/**
	 * Turns any ${type}[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<${Type}> iter(final ${type}[] array) {
		return new Iterable<${Type}>() {
			public Iterator<${Type}> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}""")


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
	#calls.append( call_template.substitute(type=type, Type=Type) )
	calls.append( iterable_template.substitute(type=type, Type=Type) )
	with open('src/main/java/net/ericaro/neoitertools/primitives/'+Type+'Iterator.java', 'w') as f:
		print "generating ", Type
		print >>f, iterator_template.substitute(type=type, Type=Type)

for call in calls:
	print call





