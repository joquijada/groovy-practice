@Grab('net.java.quickcheck:quickcheck:0.6')
import static net.java.quickcheck.generator.PrimitiveGenerators.*
import static java.lang.Math.round
import static Converter.celsius

def gen = integers(-40, 240)
def liquidC = 0..100
def liquidF = 32..212
100.times {
  int f = gen.next()
  int c = round(celsius(f))
  assert c <= f
  assert c in liquidC == f in liquidF
}