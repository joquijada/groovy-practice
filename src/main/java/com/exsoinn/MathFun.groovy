byte partitionSize = Byte.SIZE
println partitionSize
long totalPages = (Integer.MAX_VALUE+1L)*2/partitionSize
println "totalPages is $totalPages ($totalPages.class.name)"
println ((long)(Integer.MAX_VALUE+1)*2/partitionSize)

//def num = 7543
//assert num.intdiv(num) == 1
//assert num.intdiv(num + 1) == 0