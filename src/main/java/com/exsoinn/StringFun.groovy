//import business.Vendor

// Note: File named as StringFun.groovy as opposed to String.groovy  because if I declare variables of type String, Groovy will think 
//       I'm declaring String of the type that this script file defines (see [REF|GIA|p#176, first bullet point], and not java.lang.String,
//       forcing me to use the FQN of the java.lang.String type to distinguish it.
def myStr = $/Foo bar $$blah golf/$

println myStr

assert !"", 'Empty strings are always false in Groovy'


/*
 * Play around with the various GDK String methods
 */
println "myStr plus ' another string' is ${myStr + ' another string'}"

// Sum string array
String[] stringAry = ['a', 'ab', 'abc', 'abcd']
println "Sum of all string lengths in array is ${stringAry.sum()}"
StringFun thisScript = new StringFun()

List<String> myStrings = ["one", "two", "three"]
myStrings*.each {
  println "This char is $it, of type ${it.class.getName()}"
}


// TODO: Move to Misc script
//def vendor = new Vendor()
//println "${[1, 2, 3] + null}"
//def empty = []
//println empty.sum()?.size() ?: 0
String[] ary = ['one', 'two']
println "${ary.toList()}"