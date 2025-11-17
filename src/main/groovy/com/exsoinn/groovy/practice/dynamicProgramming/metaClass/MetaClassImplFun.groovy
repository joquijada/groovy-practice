package com.exsoinn.groovy.practice.dynamicProgramming.metaClass

class MyClassOne {
  def myClassOneMethod() {
    'myClassOneMethod'
  }
}

def myClassOne = new MyClassOne()
myClassOne.unknownMethod()
