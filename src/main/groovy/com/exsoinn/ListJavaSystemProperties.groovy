def propNames = System.getProperties().stringPropertyNames()
propNames.sort().each {
  def propVal = System.getProperty(it)
  println "$it --> $propVal"
}
