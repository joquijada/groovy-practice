package com.exsoinn.groovy.practice.dynamicProgramming.category

/**
 * Inspired by [REF|https://docs.groovy-lang.org/next/html/documentation/core-metaprogramming.html#categories|"A category needs not to be directly exposed to the user code, the following will also do:"]
 */
class StringCategory {
  static String doubleIt(String str) {
    str + ' ' + str
  }
}

def stringMagic = {
  String str, Closure c ->
    use(StringCategory) {
      c.call()
    }
}

String myStr = 'Am I seeing double???'
stringMagic(myStr) {
  print myStr.doubleIt()
}

