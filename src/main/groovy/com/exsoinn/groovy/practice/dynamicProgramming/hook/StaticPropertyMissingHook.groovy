package com.exsoinn.groovy.practice.dynamicProgramming.hook

class StaticPropertyMissingHookGroovyClass {
  static def $static_propertyMissing(String name) {
    return "Missing static property name is $name"
  }
}


assert StaticPropertyMissingHookGroovyClass.foobar == 'Missing static property name is foobar'
