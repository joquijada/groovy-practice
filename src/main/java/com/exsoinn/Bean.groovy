def bean = new MyBean(value: 100)
println bean.value
bean.@value = -120
println bean.value
println bean.@value
println bean.@someProp
bean.thisReference()

println "MyBean.metaClass: ${MyBean.metaClass.class.name}"
println "MyBean.metaClass.delegate: ${MyBean.metaClass.delegate.class.name}"
println "MyBean properties: ${MyBean.metaClass.properties.findAll { it.getter.name != 'getClass' }.collect { it.getter.name.toLowerCase().replaceAll(/^get/, '') }.sort()}"

class MyBean {
  //final Integer value // uncomment this and custom constructor below to achieve read-onlyness
  Integer value
  
  String someProp = "some prop value"
  
  /*public MyBean(value) {
    this.value = value
  }*/
  
  static {
    def y = new MyBean(value: 50)
    println "Invoked as 'y.@value' from static context: ${y.@value}"
  }
  
  def thisReference() {
    def x = this
    println x.@someProp
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