import business.Vendor

// Note: File named as StringFun.groovy as opposed to String.groovy  because if I declare variables of type String, Groovy will think 
//       I'm declaring String of the type that this script file defines (see [REF|GIA|p#176, first bullet point], and not java.lang.String,
//       forcing me to use the FQN of the java.lang.String type to distinguish it.
def myStr = $/Foo bar $$blah golf/$

println myStr


/*
 * Play around with the various GDK String methods
 */
println "myStr plus ' another string' is ${myStr + ' another string'}"

// Sum string array
String[] stringAry = ['a', 'ab', 'abc', 'abcd']
println "Sum of all string lengths in array is ${stringAry.sum()}"
StringFun thisScript = new StringFun()

def vendor = new Vendor()