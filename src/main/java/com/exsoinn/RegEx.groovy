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
 // ==~ returns a boolean, it internally calls Matcher.matches()
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
  assert !(subjectMatcher instanceof java.util.regex.Matcher), "${subjectMatcher.class.getName()} is not of type ${java.util.regex.Matcher.class.getName()}"
}

def jsonStr = '{"3493054":"The Chi: S3: Trailer","3462491":"City on a Hill: The Night Flynn Sent the Cops on the Ice","3462492":"City on a Hill: What They Saw in Southie High"}'

(jsonStr =~ /[^"]+","[^"]+/).eachWithIndex { it, idx ->
  println "Match $idx is $it"
} 

println jsonStr.split('","')

/*
 * Looh ahead/behind, positive and negative
 */
def negLa = /((?!portaltv).)*$/
def notTitlesApi = ~/(.(?!titles\/\d+))*/
def notTitlesSeriesApi = ~/((?!\/titles\/series\/).)*/
def missingConversion = ~/((?<!convertToSegamiImageUrlFormat).)*(\$\{URL_PREFIX|image-bin).*/
def wordBound = ~/\b\w+\bi/
def myVar = null
println "Does '/tve/json/titles/12345' match $notTitlesApi?"
assert !('/tve/json/titles/12345/blah' ==~ notTitlesApi)
assert '/tve/json/titles/series/67890' ==~ notTitlesApi
assert myVar ?: '' ==~ notTitlesSeriesApi
assert !('/tve/json/titles/series/67890' ==~ notTitlesSeriesApi)
assert '/tve/json/titles/3406304/' ==~ notTitlesSeriesApi
//assert '"${URL_PREFIX}0_0_0/0_0_0_98_382x215.jpg".toString()' ==~  missingConversion
//assert '"image-bin/images/0_0_0/0_0_0_98_382x215.jpg".toString()' ==~  missingConversion
assert !('convertToSegamiImageUrlFormat("image-bin/images/0_0_0/0_0_0_98_382x215.jpg".toString()' ==~  missingConversion)
assert !('foo i' =~ wordBound)

/*
 * Subtleity: the /.../.toString() gets evaluated before applying the `~`. The first statement below creates two distinct pattern objects,
 * hence the reason they're not equal, because Java Pattern implements equals() as identity check against the other object - if the objects
 * being compared are not the same guy, then they're not equals() as per the Pattern.equals() implementation. The second statement on the
 * other hand, by virtue of the enclosing parentheses creates the Pattern's first, then makes toString()'s them, so the comparison returns true
 */
assert ~/shit/.toString() != ~/shit/.toString()
assert (~/shit/).toString() == (~/shit/).toString()

/*
 * Learning StringGroovyMethods.isCase() for patterns: Internally this does Matcher.matches(), which tries to find match on _entire_ input
 */
def bigPattern = ~/^A|B|C|D$/ // or /A|B|C|D/ w/o beginning and end anchor, because remember it's trying to match from beginning to end
def smallPattern = ~/B/
def smallPatternDeluxe = ~/.*B.*/
assert bigPattern.isCase(smallPattern) // Does "B" equal A or B or C or D? Yes!!!
assert !smallPattern.isCase(bigPattern) // Does "B" match entire string "A or B or C or D"? No :-(
assert smallPatternDeluxe.isCase(bigPattern) // Can I find "B" at all in string "A or B or C or D"? Yes!!!

/*
 * Capturing inside non-capturing group: In side the '|' group, the groups that don't get capture
 * Still reserver a spot in te resulting array!!! For example below outputs:
 *   Found match in URL: [/12345_2_6789_02_571w.jpg, 12345, 2, 6789, 02, null, null, 571, w]
 */
def shoDotComUrl = ~/\/(\d+)_(\d+)_(\d+)_(\w+)_(?:(\d+)x(\d+)|(\d+)([wh]))\.\w+$/
url = 'https://www.showtime.com/site/image-bin/images/12345_2_6789/12345_2_6789_02_571w.jpg'
(url =~ shoDotComUrl).each {
  println "Found match in URL: $it"
}
