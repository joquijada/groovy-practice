def coll = ["269754.0","PHFOY","SONIF LAB MIA","USA","MARRIOTT"]
def str = ["269754.0","PHFOY","SONIF LAB MIA","USA","MARRIOTT"].collect {
  String retStr =it.replaceAll("(\\d+)\\.0", "\$1")
    return retStr
}.join("|")

println str


"269754.0".replaceAll("(\\d+)\\.0", "\$1")

/*
 *
 */
def range = 2..-1
println "Print items in range $range of $coll..."
coll[range].eachWithIndex { it, idx ->
  println "Item at index $idx is $it"
}
 


/*
 * Play around with flatten()
 */
List<List<Integer>> integerList = [[0, 1], [2, 3], [4, 5], [6, 7], [8, 9], [10]]
List<Integer> flattenedIntegerList = integerList.flatten()
List<Integer> result = (1..10).collect {
  println it
  integerListGenerator()
}.flatten()
println result

private List<Integer> integerListGenerator() {
  [0, 1]
}