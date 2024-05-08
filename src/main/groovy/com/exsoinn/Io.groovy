// Below // Gives error: No signature of method: OutputStream1_groovyProxy.write() is applicable for argument types: (ArrayList, Integer, Integer) values: [[65], 0, 1], 
// [REF|/Users/jmquij0106/git/career-growth/documentation/learning/groovy/learning-groovy.md|"SAM abstract classes does not work if the SAM is named the same as another method but with different signature, aka overloading"]
// greeter.greet(1, 5)
// Have to use Java way of instantiating a SAM class, see farther below
def c = {
      print 'hello'
    } as OutputStream
    
c.write([65], 0, 1)

abstract class Greeter {     
  abstract void greet(int i)     
  void greet(int i, int j) {
    print 'foo'     
  }
}

Greeter greeter = { int i ->
 print i
} as Greeter
greeter.greet(15)


def os = new OutputStream() {
  @Override
  void write(final int b) throws IOException {
    println (b as char)
  }
}

os.write([1,2,3,4] as byte[], 0, 4)


