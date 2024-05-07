byte partitionSize = Byte.SIZE
println partitionSize
long totalPages = (Integer.MAX_VALUE+1L)*2/partitionSize
println "totalPages is $totalPages ($totalPages.class.name)"
println ((long)(Integer.MAX_VALUE+1)*2/partitionSize)

//def num = 7543
//assert num.intdiv(num) == 1
//assert num.intdiv(num + 1) == 0

/*
 * Why does Integer.MIN_VALUE == -(Integer.MIN_VALUE)??? Will use Byte which is shorter range thus
 * easier to reason about mentally. Because numbers are modular in Java (I.e. add one to MAX circles back to MIN, Java never
 * throws arithmetic overflow exception), and the positive range includes 0,
 * multiplying the MIN of that numeric type by -1 makes it one greater than MAX, which forces it to circle back
 * to MIN, again because of the modular nature ofnumeric types.
 */
println "Byte max is $Byte.MAX_VALUE"
println "Byte min is $Byte.MIN_VALUE"
assert -Byte.MIN_VALUE == Byte.MIN_VALUE
// But a widening operation on -1 to short increases Byte.MIN_VALUE to 128
assert Integer.MAX_VALUE + 1L == -1L * Integer.MIN_VALUE