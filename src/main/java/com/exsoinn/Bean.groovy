def bean = new MyBean(value: 100)
println bean.value

class MyBean {
  public value
  
  static {
    def y = new MyBean(value: 50)
    println "Invoked as 'y.@value' from static context: ${y.@value}"
  }
  
  void setValue(value) {
    this.value = value
  }
  
  def getValue() {
    def x = this
    //println x.value // Results in StackOverflow
    println  "Invoked as 'x.@value', where x == this: ${x.@value}"
     println  "Invoked as 'this.value': ${this.value}"
    value * 2
  }
}