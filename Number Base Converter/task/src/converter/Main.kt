package converter

import java.math.BigInteger

const val BASE_DEC = 10
const val baseSym = "0123456789ABCDEF"

fun main() {
    println("Hello, world!")
    var resultDec = BigInteger.ZERO
    var resultStr = ""
    while (true) {
        println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val answer1 = readln()
        if (answer1 == "/exit") return
        val (sb, tb) = answer1.split(" ")
        val sourceBase = sb.toInt()
        val targetBase = tb.toInt()
        while (true) {
            println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
            val answer2 = readln()
            if (answer2 == "/back") break
            resultDec = AnyBaseToDec(answer2, sourceBase.toInt())
            resultStr = DecToAnyBase(resultDec, targetBase)
        }
    }
}

fun AnyBaseToDec(number: String, base: Int): BigInteger {
    var result = BigInteger.ZERO
    for (sym in number) {
        val idx = baseSym.indexOf(sym)
        result = base.toBigInteger() * result + idx.toBigInteger()
    }
    return result
}


fun DecToAnyBase(number: BigInteger, base: Int): String {
    print("Enter number in decimal system: ")
    var number = readln().toInt()
    print("Enter target base: ")
    val base = readln().toInt()
    var result = ""
    do {
        result += baseSym[number % base]
        number /= base
    } while (number > 0)
    result = result.reversed()
    return result
}
