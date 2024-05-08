//def vendor = new Vendor()
//println "${[1, 2, 3] + null}"
//def empty = []
//println empty.sum()?.size() ?: 0
//String[] ary = ['one', 'two']
//println "${ary.toList()}"

def list1 = ['a', 'b', 'c']
def list2 =['a', 'b', 'c', 'd']

println list1 - list2
println list2 - list1

print list1 + 'e'