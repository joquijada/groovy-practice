package com.exsoinn.groovy.practice.dynamicProgramming.hook

class PropertyMissingHookGroovyClass {
  def storage = [:]

  def propertyMissing(String name, value) { storage[name] = value }

  def propertyMissing(String name) {
    storage[name]
  }
}

def f = new PropertyMissingHookGroovyClass()
f.foo = "bar"

assert f.foo == "bar"
