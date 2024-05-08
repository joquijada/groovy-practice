@Grab(group='joda-time', module='joda-time', version='2.12.1')

import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.DateTime

// DateTimeZone et = DateTimeZone.forOffsetHours(-5)
DateTimeZone utc = DateTimeZone.forID('GMT')
DateTime dt = new DateTime(utc)
DateTimeFormatter fmt = DateTimeFormat.forPattern('E, d MMM, yyyy HH:mm:ssz')
//fmt.print(dt)

StringBuilder now = new StringBuilder()
fmt.printTo(now, dt)
println now.toString()