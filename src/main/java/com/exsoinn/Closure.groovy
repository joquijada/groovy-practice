/*def str = [269754.0,"PHFOY","SONIF LAB MIA","USA","MARRIOTT"].each {
  String retStr = it.replaceAll("(\\d+)\\.0", "\$1")
    return retStr
}.join("|")

println str*/

def obj1 = new Expando(a: 1, b: 2)
def obj2 = new Expando(a: 1, b: 2)
def someList = []


// Variables delacred in closure local scope
def listOfMapsOne = [['a': 1], ['b': 2]]
def listOfMapsTwo = [['c': 3], ['d': 4]]

def cumulativeMap = [:]
def clearedMap = [:]
listOfMapsOne.each { Map one -> 
  listOfMapsTwo.each { Map two ->
    clearedMap.clear()
    cumulativeMap << one << two
    clearedMap << one << two
    def localMap = [:]
    localMap << one << two
    println "localMap is ${localMap}"
  }
}

println cumulativeMap
println clearedMap

/*
 * Is it a closure or what???
 */
def whatIsIt = {
  encode {-> println "screw you"}
}
println whatIsIt.class.getName()
 
