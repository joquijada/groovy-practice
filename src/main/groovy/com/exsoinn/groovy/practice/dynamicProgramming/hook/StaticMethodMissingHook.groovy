package com.exsoinn.groovy.practice.dynamicProgramming.hook

class StaticMethodMissingHookGroovyClass {
  static def $static_methodMissing(String name, Object args) {
    return "Missing static method name is $name"
  }
}

assert StaticMethodMissingHookGroovyClass.bar() == 'Missing static method name is bar'

