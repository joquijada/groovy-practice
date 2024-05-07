// import javafx.util.Pair


def myList = ['a', 'b', 'c']
myList.withIndex().find { e, idx -> 
  println "$e ==> $idx"
  true
}


def crap = [key: 'jussy']

// Append an entry to Map
crap << [key2: 'punt']


println crap


/*
 * For maps with a single entry, argument can be passed with or w/o square brackets
 */
myPrintMap([key2: 'jussy2'])
myPrintMap(key2: 'jussy2') // w/o square brackets

def myPrintMap(Map inMap) {
  println inMap

}


def listOfMaps = [[key1: 'jussy1'], [key2: 'jussy2']]
println "listOfMaps is $listOfMaps, ${listOfMaps.class.getName()}"

// def pair = ['key3', 'jussy3'] as Pair
// println pair

//def pair2 = ('key3'): 'jussy3' as Pair

def emptyMap = [:]
println emptyMap

println emptyMap.collectEntries {}

def map2 = [key1: 'jussy1', key2: 'jussy2']
println map2