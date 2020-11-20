

def args = [1, 2]

foo(*args, false)


void foo(Integer arg1, Integer arg2, boolean flag) {
  print "$arg1, $arg2, $flag"
}