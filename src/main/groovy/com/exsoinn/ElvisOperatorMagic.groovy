class MyElvisOperatorMagicInnerClass {
  String propOne = null
  String propTwo = 'hi'
}

def obj1 = null


println (obj1?.propOne ?: false)

obj1 = new MyElvisOperatorMagicInnerClass()

println (obj1?.propOne ?: false)

println (obj1?.propTwo ?: false)

obj1 = null
println (obj1?.propOne)
