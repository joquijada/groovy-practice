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


[[2, 3],[4, 5, 6]].eachCombination { println "Found $it" }

[2, 3, 4, 5, 6].eachCombination { println "Found $it" }


/*
 * collectMany() with index???
 */
[[1, 2], [3, 4]].withIndex().each {item, idx ->
  println "$item $idx"
}

/*
 * The spread operator
 * Confusion: Using the `*.` operator still collects the result into 
 * a list [REF|https://docs.groovy-lang.org/latest/html/documentation/core-operators.html|"It is equivalent to calling the action on each item and collecting the result into a list"]
 * Below ouputs `['[1, 2]', '[3, 4]', '[5, 6]']`, notice how an array was generated of the result of invoking `toString()` on each of the inner arrays
 */
println "\nGoofing around with the '*.' operator"
def ary = [ [1, 2], [3, 4], [5, 6] ]
println (ary*.flatten().toString())
println (ary.flatten())

// To simulate how legacy cache stores images, and how to get a set of URL's
def images = [1234: ['a', 'b', 'c'], 5678: ['d', 'e', 'f']]
images.values()*.first()

// Exercise to validate (orderInfo.approximateItemSizeInBytes() + updates*.value.toString().getBytes('UTF-8').length) > DYNAMODB_MAX_ITEM_SIZE_IN_BYTES
// line in Charcot fulfillment. Specifically, doesn't this produce an array of the lengths, in which I'm missing a sum() call at the end?
def myStrs = ['one', 'two', 'three']
println "Bytes length of each string is ${myStrs*.getBytes('UTF-8').length}"
myStrs*.getBytes('UTF-8').length.sum()
//0 + myStrs*.getBytes('UTF-8').length

// Dows [].findResults return an empty list if all input items are null?
println ([null].findResults {
  it
})

println ([null, 1, 2, 3, 4, [6, 7, 8]].findResults {
  it
}.flatten())