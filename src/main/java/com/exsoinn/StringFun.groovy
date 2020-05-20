//import business.Vendor
import java.util.regex.Pattern

// Note: File named as StringFun.groovy as opposed to String.groovy  because if I declare variables of type String, Groovy will think 
//       I'm declaring String of the type that this script file defines (see [REF|GIA|p#176, first bullet point], and not java.lang.String,
//       forcing me to use the FQN of the java.lang.String type to distinguish it.
def myStr = $/Foo bar $$blah golf/$

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