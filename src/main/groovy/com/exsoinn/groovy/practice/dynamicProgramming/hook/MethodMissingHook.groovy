package com.exsoinn.groovy.practice.dynamicProgramming.hook

class MethodMissingHookGroovyClass {
  def methodMissing(String name, args) {
    return 'this is me'
  }
}

/**
 * Below 'methodMissing() will get executed for POGO's that do not implement 'GroovyInterceptable' marker, after
 * meta class and target class have been searched for a method or a closure
 */
class GORM {
  def dynamicMethods = []
  // an array of dynamic methods that use regex

  def methodMissing(String name, args) {
    def method = dynamicMethods.find { it.match(name) }
    if (method) {
      GORM.metaClass."$name" = { Object[] varArgs ->
        method.invoke(delegate, name, varArgs)
      }
      return method.invoke(delegate, name, args)
    } else
      throw new MissingMethodException(name, delegate, args)
  }
}

assert new MethodMissingHookGroovyClass().someUnknownMethod(42l) == 'this is me'
