import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

def json = '{"WCID":["SONIFID", "FOO", "BLAH"], "TheirId":["TheirIdAlias", "AnotherTheirIdAlias"]}'

def slurper = new JsonSlurper()
def res = slurper.parseText(json)

def map = new HashMap()
res.each {
  println it.dump(); 
  it.each { 
    alias -> alias.value.each { aliasEnt -> 
     println "alias of $alias.key is $aliasEnt"
     map.put(aliasEnt, alias.key)
    }
  }
}


println map