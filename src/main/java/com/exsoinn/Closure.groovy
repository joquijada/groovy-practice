def str = [269754.0,"PHFOY","SONIF LAB MIA","USA","MARRIOTT"].each {
  String retStr = pStr.replaceAll("(\\d+)\\.0", "\$1")
    return retStr
}.join("|")

println str