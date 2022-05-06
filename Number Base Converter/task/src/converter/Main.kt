package converter

import java.math.BigInteger

const val BASE_DEC = 10
const val baseSym = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun main() {
    println("Hello, world!")
    var resultDec = BigInteger.ZERO
    var resultStr = ""
    val regex = "\\s+".toRegex()
    while (true) {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
        val answer1 = readln()
        if (answer1 == "/exit") break
//        val (sb, tb) = answer1.split(" ")
        val (sb, tb) = answer1.split(regex).map { it.toInt() }
        val sourceBase = sb.toInt()
        val targetBase = tb.toInt()
        while (true) {
            print("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back) ")
            val answer2 = readln()
            if (answer2 == "/back") break
            resultDec = AnyBaseToDec(answer2.uppercase(), sourceBase)
            resultStr = DecToAnyBase(resultDec, targetBase)
            println("Conversion result: $resultStr\n")
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
    var result = ""
    var quotient = number
    val baseBig = base.toBigInteger()
    do {
        val remainder = quotient % baseBig
        result += baseSym[remainder.toInt()]
        quotient = quotient / baseBig
    } while (quotient > BigInteger.ZERO)
    result = result.reversed()
    return result
}
