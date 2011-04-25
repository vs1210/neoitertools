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

def gen_primitives():
	"""generate primitive array Generator"""
	calls = []
	for type, Type in types:
		calls.append( call_template.substitute(type=type, Type=Type) )
		with open('src/main/java/net/ericaro/neoitertools/generators/primitives/'+Type+'Generator.java', 'w') as f:
			print "generating ", Type
			print >>f, iterator_template.substitute(type=type, Type=Type)

	for call in calls:
		print call


import os
print "parsing"
os.chdir('src/main/java')
srcfile=[]
for root, dirs, files in os.walk("."):
    print root, dirs, files
    srcfile+=[ (root[2:].replace("/",'.'),f[0:-5]) for f in files if f.endswith(".java") ]

# move to target of wiki
os.chdir('../../../../neoitertools-wiki')

wiki_template = string.Template("""#summary $type wiki
#labels Javadoc-Wiki
#sidebar TableOfContents

Leave a comment here to discuss issues about $package.$type
""")

for package, type in srcfile:
		print package, type
		with open(type+'.wiki', 'w') as f:
			print "generating wiki ", type
			print >>f, wiki_template.substitute(type=type, package = package)


