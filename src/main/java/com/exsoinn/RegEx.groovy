import java.util.regex.*


String str =  "123456.0"
//String str =  "ABCD"
println str.replaceAll("(\\d+)\\.0", "\$1")

/*Pattern patt = Pattern.compile("^(\\d+)(\\.0)?")

Matcher m = patt.matcher(str)

m.find()*/
//println m.group()


/*
 * Inspect what a matcher returns w/o groupings versus w/ groupings
 */
 def matcherWithoutGroupings = 'a:1 b:2 c:3' =~ /\S:\S/

matcherWithoutGroupings.each {
  println "Match w/o groupings: $it"
  println "$it is ${it.class.getName()}"
}

def matcherWithGroupings = 'a:1 b:2 c:3' =~ /(\S):(\S)/

matcherWithGroupings.each {
  println "Match with groupings: $it"
} 

println "First match, group 1 is ${matcherWithGroupings[0][1]}"


def res = -["one", "two", "three"].count {it}
println "res is $res"

/*
 * Explicit pattern creation
 */
def pattern = ~$/.*/$
println "pattern is ${pattern.toString()}"


/*
 * Pattern equality check. GroovyDefaultMethods has an isCase() for Pattern, that does a "toString()" on the case value,
 * before running the Pattern to see if it matches that value.
 */
def pattA = ~/.*/
def pattB = ~/.*/

println "$pattA == $pattB ? ${pattA == pattB}"

switch (pattA) {
  case pattB:
    println "$pattA == $pattB according to switch statement"
    break;
  default:
     println "$pattA != $pattB according to switch statement"
}


/*
 * Using String.eachMatch(Pattern)
 */
def url = 'https://www.showtime.com/site/image-bin/images/1033823_0_0/1033823_0_0_99_264x198.jpg'
def titlePattern = /(\d+)_(\d+)_(\d+)_(\d{2})_(\d+)x(\d+)\.(\w+)/
url.eachMatch(~titlePattern) {
  println "Using 'String.eachMatch' found: $it"
}

/*
 * Matcher (==~) versus Finder (=~)
 */
def fullRegionMatcher = url ==~ /(\d+_)/
fullRegionMatcher.each() {
  println "Full region matcher found? $it"
}

def findMatcher = url =~ /(\d+)_/
if (findMatcher) {
  println "found something"
  println "Matcher type is ${findMatcher.class.getName()}"
} else {
  println "did not find anything"
}
println "findMatcher is $findMatcher"
findMatcher.each() {
  println "Find matcher found $it"
  println "${it[1]} is ${it[1].class.getName()}"
}


// Can matchers (==~) be use to capture groups??? Apparently not, because Matcher is of type Java Boolean
def subject = "my test string"
def subjectPattern = ~/m(.+)g/
def subjectMatcher = subject ==~ subjectPattern
if (subjectMatcher) {
  println "Matched subject"
  println "Matcher type is ${subjectMatcher.class.getName()}"
  assert subjectMatcher instanceof java.util.regex.Matcher
}