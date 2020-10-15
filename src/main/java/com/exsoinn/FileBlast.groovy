
/*
 * How to define a URI
 */
URI uri = new URI('/foo.txt')
File file = new File('/foo.txt')
file.toURI()
file = new File(getClass().getResource('./StringFun.groovy').toURI())
