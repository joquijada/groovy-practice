package com.exsoinn.groovy.practice.dynamicProgramming.metaClass

// file: IntegerMetaClass.groovy
// package groovy.runtime.metaclass.java.lang;

class IntegerMetaClass extends DelegatingMetaClass {
  IntegerMetaClass(MetaClass metaClass) { super(metaClass) }
  IntegerMetaClass(Class theClass) { super(theClass) }
  Object invokeMethod(Object object, String name, Object[] args) {
    if (name =~ /isBiggerThan/) {
      def other = name.split(/isBiggerThan/)[1].toInteger()
      object > other
    } else {
      return super.invokeMethod(object,name, args);
    }
  }
}

// File testInteger.groovy
def i = 10

assert i.isBiggerThan5()
assert !i.isBiggerThan15()

println i.isBiggerThan5()

// Execute as follows:
// groovy -cp . testInteger.groovy
// Method calls to isBiggerThan*() will be intercepted

