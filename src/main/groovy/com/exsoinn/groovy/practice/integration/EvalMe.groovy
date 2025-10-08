package com.exsoinn.groovy.practice.integration

/**
 * https://docs.groovy-lang.org/latest/html/documentation/guide-integrating.html#_integrating_groovy_in_a_java_application
 */
class EvalMe {
  static void evalMe() {
    assert Eval.x(4, '2*x') == 8
    assert Eval.me('k', 4, '2*k') == 8
    assert Eval.xy(4, 5, 'x*y') == 20
    assert Eval.xyz(4, 5, 6, 'x*y+z') == 26
  }

  static void main(String[] args) {
    evalMe()
  }
}
