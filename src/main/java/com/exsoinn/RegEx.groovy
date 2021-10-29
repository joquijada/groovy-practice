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
 * Matcher.findAll() in action!!!
 */
println ""
def findAllPatt = ~/\d+_/
res = (url =~ findAllPatt).findAll()
println "Matcher.findAll() result on pattern '$findAllPatt' is $res"

// Notice how the introduction of a capturing group changes result to a multi-dimensional array (an array of arrays), unlike previous one
// which is just a one-dimensional array of scalars
findAllPatt = ~/(\d+)_/
res = (url =~ findAllPatt).findAll()
println "Matcher.findAll() result on pattern '$findAllPatt' is $res"
println ""


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


// Can matches (==~) be use to capture groups??? Apparently not, because Matcher is of type Java Boolean
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

/*
 * The below negative lookahead's are saying "I will match any string without
 * a trailing '/titles/<some-num>' and '/titles/series/' substring respectively
 */
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

// Match a string that begins with 'q' where 'q' is not followed by a 'u'
assert 'qiuttttu' ==~ 'q(?!u).+'

// Does the same as above but since we're search in any section of the input sequence it succeeds
assert 'qiuttttu' =~ 'q(?!u)'



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

/*
 * More fun with match groupings
 */
print "\n\n\n"
String fightImageUrl = 'https://segami.showtime.com/segami/46/0/3440434/02-05-06-07-08/180x270/image.jpg'
Pattern franchiseIdPattern = ~$//(46|93|684|841|860|5826)//$

// but if pattern fails to match, getAt(0) throws IndexOutofBoundsException, because I guess Matcher is never NULL, even when using the '?.' notation. Does this
// mean that '?.' is only for checking strictly NULL nor not (I.e. a 'false' Boolean object is considered non-NUL and therefore passes the test?). See the test farther below
//println ((fightImageUrl =~ franchiseIdPattern)?.getAt(0)?.getAt(1))

// Therefore try the iterative approach. Below will NOT throw any exception if pattern fails to match
(fightImageUrl =~ franchiseIdPattern).each {
  println it[1]
}
println (fightImageUrl =~ franchiseIdPattern) ?: 'Did not find crap'
if (fightImageUrl =~ franchiseIdPattern) {
  println 'Found it!!!'
}

// Test whether '?.' operator works by doing a truthy-type check, or if it does a strictly NULL check
assert Boolean.FALSE?.hashCode() > 0, 'Boolean.FALSE evaluated to false by `?.` operator - this will never happen'
assert Integer.valueOf(0)?.hashCode() == 0, '"0" evaluated to false by `?.` operator - this will never happen either for same reason as above'
print "\n"
// Even the GDK findResult() method does strict NULL check, not a weak falsy/truthy type check ala ECMAScript aka JavaScript
def foundRes = [1, 2, 3].findResult {
   Boolean.FALSE
}
println foundRes

// I don't need capturing groups for something simple like this (04/27/2021)
println (('SON0274042' =~ /[a-zA-Z]+/)[0])

// Fucking shit
def routeRegEx = ~$/^(?<=//)\s*\$$app->(get|post|delete|put)\('([^']+)'/$
def crap = "//\$app->get('/api/title/endplay/title/:titleId/at/:at'="
println routeRegEx
def m = crap =~ routeRegEx
if (m) {
  println "Matched $routeRegEx on $crap: ${m[0]}"
} else {
  println "RegEx $routeRegEx did not match $crap"
}