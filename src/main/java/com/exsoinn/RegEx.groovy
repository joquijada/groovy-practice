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
}

def matcherWithGroupings = 'a:1 b:2 c:3' =~ /(\S):(\S)/

matcherWithGroupings.each {
  println "Match with groupings: $it"
}