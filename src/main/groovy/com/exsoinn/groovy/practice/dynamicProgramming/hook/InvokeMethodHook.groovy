package com.exsoinn.groovy.practice.dynamicProgramming.hook

/**
 * Need to implement GroovyInterceptable marker for Groovy to invoke 'invokeMethod()'
 * on every method call.
 */
class InvokeMethodHookGroovyClass implements GroovyInterceptable {
  def property1 = 'ha'
  def field2 = 'ho'
  def field4 = 'hu'
  String property

  def getField1() {
    return 'getHa'
  }

  def getProperty(String name) {
    if (name != 'field3')
      return metaClass.getProperty(this, name)
    else
      return 'field3'
  }

  /**
   * https://docs.groovy-lang.org/next/html/documentation/core-metaprogramming.html#_invokemethod
   * Meant to be used with 'groovy.lang.GroovyInterceptable' or MetaClass to
   * intercept all method calls. Use of this for missing methods is discouraged, use
   * 'missingMethod()' instead.
   */
  def invokeMethod(String name, Object args) {
    // Below print() call will result in StackOverflowError, [REF|https://docs.groovy-lang.org/next/html/documentation/core-metaprogramming.html#_groovyinterceptable|"We cannot use default groovy methods like println because these methods are injected into all Groovy objects so they will be intercepted too"]
    // print "called invokeMethod $name $args"
    System.out.println "called invokeMethod $name $args"
    return "called invokeMethod $name $args"
  }


  def test() {
    return 'method exists'
  }
}

def invokeMethodHookGroovyClass = new InvokeMethodHookGroovyClass()

// get/setProperty
assert invokeMethodHookGroovyClass.field1 == 'getHa'
assert invokeMethodHookGroovyClass.field2 == 'ho'
assert invokeMethodHookGroovyClass.field3 == 'field3'
assert invokeMethodHookGroovyClass.field4 == 'hu'

// invokeMethod()
assert invokeMethodHookGroovyClass.test() == 'called invokeMethod test []'
assert invokeMethodHookGroovyClass.someMethod() == 'called invokeMethod someMethod []'
