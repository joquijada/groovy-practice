package com.exsoinn.groovy.practice.dynamicProgramming.hook

import groovy.xml.Entity

/*
 * This is an alternate way to hook into 'invokeMethod()' w/o implementing the 'GroovyInterceptable'
 * marker. Instead we do it via the MetaClass. Again recall the priority of calling hierarchy, where the
 * 'invokeMethod()' in MetaClass is given a try, [REF|learning-groovy.md|"As per the priority hierarchy at"]
 */
String invoking = 'ha'
invoking.metaClass.invokeMethod = { String name, Object args ->
  'invoked'
}

assert invoking.length() == 'invoked'
assert invoking.someMethod() == 'invoked'


Entity entity = new Entity('Hello')
entity.metaClass.invokeMethod = { String name, Object args ->
  'invoked'
}

assert entity.build(new Object()) == 'invoked'
assert entity.someMethod() == 'invoked'
