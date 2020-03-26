Integer i = 1

if (!i) {
  println 'null'
} else {
  println 'not null'
}


// Empty lists evaluate to true when coerced to do NULL check
myList = null
assert !myList


myList = []
assert !myList

myList << 'foo'
assert myList