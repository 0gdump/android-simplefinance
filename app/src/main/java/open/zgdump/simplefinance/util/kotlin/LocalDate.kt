package open.zgdump.simplefinance.util.kotlin

import kotlinx.datetime.LocalDate

private const val DAYS_PER_CYCLE = 146097
private const val DAYS_0000_TO_1970 = DAYS_PER_CYCLE * 5 - (30 * 365 + 7)

fun LocalDate.isLeapYear() = year and 3 == 0 && (year % 100 != 0 || year % 400 == 0)

fun LocalDate.toEpochDay(): Int {
    val y = year
    val m = monthNumber
    val day = dayOfMonth
    var total = 365 * y
    if (y >= 0) {
        total += (y + 3) / 4 - (y + 99) / 100 + (y + 399) / 400
    } else {
        total -= y / -4 - y / -100 + y / -400
    }
    total += (367 * m - 362) / 12
    total += day - 1
    if (m > 2) {
        total--
        if (!isLeapYear()) {
            total--
        }
    }
    return total - DAYS_0000_TO_1970
}

fun ofEpochDay(epochDay: Int): LocalDate {
    var zeroDay = epochDay + DAYS_0000_TO_1970
    // find the march-based year
    zeroDay -= 60 // adjust to 0000-03-01 so leap day is at end of four year cycle

    var adjust = 0
    if (zeroDay < 0) {
        // adjust negative years to positive for calculation
        val adjustCycles = (zeroDay + 1) / DAYS_PER_CYCLE - 1
        adjust = adjustCycles * 400
        zeroDay += -adjustCycles * DAYS_PER_CYCLE
    }
    var yearEst = (400 * zeroDay + 591) / DAYS_PER_CYCLE
    var doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400)
    if (doyEst < 0) {
        // fix estimate
        yearEst--
        doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400)
    }
    yearEst += adjust // reset any negative year

    val marchDoy0 = doyEst

    // convert march-based values back to january-based
    val marchMonth0 = (marchDoy0 * 5 + 2) / 153
    val month = (marchMonth0 + 2) % 12 + 1
    val dom = marchDoy0 - (marchMonth0 * 306 + 5) / 10 + 1
    yearEst += marchMonth0 / 10

    // check year now we are certain it is correct
    val year = yearEst//ChronoField.YEAR.checkValidIntValue(yearEst)
    return LocalDate(year, month, dom)
}