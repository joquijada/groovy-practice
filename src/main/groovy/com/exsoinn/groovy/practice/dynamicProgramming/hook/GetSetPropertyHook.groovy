package com.exsoinn.groovy.practice.dynamicProgramming.hook

class GetSetPropertyHookGroovyClass {
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

  void setProperty(String name, Object value) {
    this.@"$name" = 'overridden'
  }
}

def getSetPropertyGroovyClass = new GetSetPropertyHookGroovyClass()

// get/setProperty
assert getSetPropertyGroovyClass.field1 == 'getHa'
assert getSetPropertyGroovyClass.field2 == 'ho'
assert getSetPropertyGroovyClass.field3 == 'field3'
assert getSetPropertyGroovyClass.field4 == 'hu'
getSetPropertyGroovyClass.property = 'a'

assert getSetPropertyGroovyClass.property == 'overridden'
