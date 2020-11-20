//import business.Vendor
import java.util.regex.Pattern

// Note: File named as StringFun.groovy as opposed to String.groovy  because if I declare variables of type String, Groovy will think 
//       I'm declaring String of the type that this script file defines (see [REF|GIA|p#176, first bullet point], and not java.lang.String,
//       forcing me to use the FQN of the java.lang.String type to distinguish it.
// Forward slash doesn't need escaping in dollar-slashy string, and last character can be a backslash
def myBastard = 'bastard'
//def myStr = $/Foo bar/ $myBastard ${'\\'}u005B $$blah golf\/$
def myStr = $/Foo bar/ $ $myBastard \u005Cu005B $$blah golf\/$
println myStr

myStr = /${'$'}foo/
println myStr

myStr = $/Foo bar/ $ $myBastard \u005Cu005B $$blah golf\/$
println myStr

assert !"", 'Empty strings are always false in Groovy'

// Need to toString a GString when assigning to a String???
String thizString = "thiz string is a GString ${myStr}"
println thizString

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

// Strip indent
def multiline = '''    
           Every series. Entire seasons. All-new episodes.
           |Hit movies, documentaries, sports & more.
           |Commercial-free access to live and on demand TV.
           |Watch anywhere - on your TV, tablet, phone or computer.'''
println "Original: $multiline"
println "\nstripIndent: ${multiline.stripIndent()}"
println "\ntrim: ${multiline.trim()}"

// the (?m) turns on multiline mode, see https://stackoverflow.com/questions/1363643/regex-over-multiple-lines-in-groovy. Conversely
// can pass Pattern.MULTILINE as second argument to Pattern.compile() call
def leadSpace = /(?m)^\s+/

//def fuckYou = /replaceAll: ${multiline.replaceAll('\/^\\s+\/g', '')}/
//println fuckYou
//println /replaceAll: ${multiline.replaceAll('^\s+', '')}/

println "\nRegEx $leadSpace: ${Pattern.compile(leadSpace).matcher(multiline).replaceAll('')}"

println "\nstripMargin('|'): ${multiline.stripMargin('|')}"


/*
 * Recipe for building array of X repetitions of a string. Can also use
 * Collections.nCopies(), http://docs.groovy-lang.org/latest/html/documentation/working-with-collections.html#_duplicating_elements
 */
def myJson = '{"blah": "foo"}'
def res = ((myJson + ', ') * 5)[0..-3] // exclude trailing spaces and comma
res = res.split(',').collect { it }

println "Array of X JSON is $res"
println "Array of X JSON using JDK API is ${Collections.nCopies(5, myJson)}"

// Do I need to escape backlash in single/double quoted strings that are regex'? Let's see...Ah, yes I do. The top one 
// fails to match anything because the compiler consumes the `\`'s.
def myRegEx = ~'\b.+\b'
//def myRegEx = ~/\b.+\b/
("word1 word2" =~ myRegEx).each {
  println "Match found is $it"
}

myRegEx = ~'.+'
("word3 word4" =~ myRegEx).each {
  println "Match found is $it"
}

