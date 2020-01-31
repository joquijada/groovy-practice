import java.util.regex.*


String str =  "123456.0"
//String str =  "ABCD"
println str.replaceAll("(\\d+)\\.0", "\$1")

/*Pattern patt = Pattern.compile("^(\\d+)(\\.0)?")

Matcher m = patt.matcher(str)

m.find()*/
//println m.group()