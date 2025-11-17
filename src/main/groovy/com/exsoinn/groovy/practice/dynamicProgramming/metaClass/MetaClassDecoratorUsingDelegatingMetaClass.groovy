package com.exsoinn.groovy.practice.dynamicProgramming.metaClass

class Foo {
  def bar() { "bar" }
}

class MyFooMetaClass extends DelegatingMetaClass {
  MyFooMetaClass(MetaClass metaClass) { super(metaClass) }

  MyFooMetaClass(Class theClass) { super(theClass) }

  Object invokeMethod(Object object, String methodName, Object[] args) {
    def result = super.invokeMethod(object, methodName.toLowerCase(), args)
    result.toUpperCase();
  }
}


def mc = new MyFooMetaClass(Foo.metaClass)
mc.initialize()

Foo.metaClass = mc
def f = new Foo()

assert f.BAR() == "BAR" // the new metaclass routes .BAR() to .bar() and uppercases the result
