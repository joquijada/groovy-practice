def propertyMissing2(String name) {
  int result = 0
  name.each {
    result <<= 1
    //println "" // throws StackOverFlow exception because the property missing hook is invoked recursively
    if (it == 'I') result++
  }
  return result
}

//println IIOI + IOI

/*
 * Does the JVM use 2'a complement?
 */
def five = 5
def negFive = -5
println "$five in binary is ${Integer.toBinaryString(five)}" 
println "$negFive in binary is ${Integer.toBinaryString(negFive)}" 
