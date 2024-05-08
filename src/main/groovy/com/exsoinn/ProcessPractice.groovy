// Find a process by name, [REF|https://stackoverflow.com/questions/28160347/getting-pid-for-a-process-using-groovy|""]
// println 'ps -A'.execute().text.split('\n').find { it.contains 'java' }?.split()?.first()

// 
println $/vm_stat | perl -ne '/page size of (\d+)/ and $$size=$1; /Pages\s+([^:]+)[^\d]+(\d+)/ and printf("%-16s % 16.2f Mi\n", "$1:", $2 * $$size / 1048576);'/$.execute().text


//print 'perl -v | grep perl'.execute().text

def pid = 'pgrep -fa fulfillment'.execute().text
println pid.class.name

println "Fulfillment PID: ${'pgrep -f fulfillment'.execute().text}"

def fd = """
  total 0
lrwx------ 1 root root 64 Oct 17 11:57 0 -> /dev/pts/0
lrwx------ 1 root root 64 Oct 17 11:57 1 -> /dev/pts/0
lrwx------ 1 root root 64 Oct 17 11:57 10 -> 'socket:[55788]'
lrwx------ 1 root root 64 Oct 17 11:57 11 -> 'anon_inode:[eventpoll]'
lrwx------ 1 root root 64 Oct 17 11:57 12 -> 'anon_inode:[eventfd]'
lrwx------ 1 root root 64 Oct 17 11:57 13 -> 'socket:[61787]'
lrwx------ 1 root root 64 Oct 17 11:57 14 -> 'socket:[63643]'
lr-x------ 1 root root 64 Oct 17 11:57 16 -> /dev/urandom
lrwx------ 1 root root 64 Oct 17 11:57 17 -> 'anon_inode:[eventpoll]'
lrwx------ 1 root root 64 Oct 17 11:57 18 -> 'anon_inode:[eventfd]'
lrwx------ 1 root root 64 Oct 17 11:57 19 -> 'anon_inode:[eventpoll]'
lrwx------ 1 root root 64 Oct 17 11:57 2 -> /dev/pts/0
lrwx------ 1 root root 64 Oct 17 11:57 20 -> 'anon_inode:[eventfd]'
lrwx------ 1 root root 64 Oct 17 11:57 21 -> 'anon_inode:[eventpoll]'
lrwx------ 1 root root 64 Oct 17 11:57 22 -> 'anon_inode:[eventfd]'"""

def fdMatch = fd =~ /\d+ ->/
fdMatch.each {
  println it
}
println "FD Match Size: ${fdMatch.size()}"