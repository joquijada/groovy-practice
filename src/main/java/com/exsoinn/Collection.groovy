def str = ["269754.0","PHFOY","SONIF LAB MIA","USA","MARRIOTT"].collect {
  String retStr =it.replaceAll("(\\d+)\\.0", "\$1")
    return retStr
}.join("|")

println str


"269754.0".replaceAll("(\\d+)\\.0", "\$1")