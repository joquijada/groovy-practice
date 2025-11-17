package com.exsoinn.groovy.practice.dynamicProgramming.category

class Distance {
  def number
  String toString() { "${number}m" }
}

@Category(Number)
class NumberCategory {
  Distance getMeters() {
    new Distance(number: this)
  }
}

use (NumberCategory)  {
  assert 42.meters.toString() == '42m'
}

