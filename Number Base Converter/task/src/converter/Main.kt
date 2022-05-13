package converter

import java.math.BigInteger
import java.math.BigDecimal
import java.math.RoundingMode

const val ROUND_LIMIT = 5
const val baseSym = "0123456789abcdefghijklmnopqrstuvwxyz"

fun main() {
    println("Hello, world!")
    var resultStr: String
    val regex = "\\s+".toRegex()
    while (true) {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
        val answer1 = readln().lowercase()
        if (answer1 == "/exit") break
        val (sourceBase, targetBase) = answer1.split(regex)
        if (sourceBase.isEmpty() || targetBase.isEmpty()) continue
        while (true) {
            print("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back) ")
            val answer2 = readln().lowercase()
            if (answer2 == "/back") break
            resultStr = AnyBaseToAnyBase(answer2, sourceBase, targetBase)
            println("Conversion result: $resultStr\n")
        }
    }
}

fun AnyBaseToAnyBase(number: String, sourceBase: String, targetBase: String): String {
    if (sourceBase == "10") {
        return DecToAnyBase(number, targetBase)
    } else if (targetBase == "10") {
        return AnyBaseToDec(number, sourceBase)
    } else {
        return DecToAnyBase(AnyBaseToDec(number, sourceBase), targetBase)
    }
}

//From any base to decimal base
fun AnyBaseToDec(number: String, sourceBase: String): String {
    val parts = number.split(".")
    var result = IntAnyBaseToDec(parts[0], sourceBase)
    if (parts.size > 1 && parts[1].isNotEmpty()) {
        result = "$result.${FractalAnyBaseToDec(parts[1], sourceBase)}"
    }
    return result
}

/////////////////////////////////////////////////////////////////////
//Integer part from any base to decimal
fun IntAnyBaseToDec(number: String, sourceBase: String): String {
    var result = BigInteger.ZERO
    val baseBig = sourceBase.toBigInteger()
    val numberRev = number.reversed()
    for (idx in number.indices) {
        val sym = numberRev[idx]
        val num = baseSym.indexOf(sym)
        result = result + num.toBigInteger() * baseBig.pow(idx)
    }
    return result.toString()
}

//Fractal part from any base to decimal
fun FractalAnyBaseToDec(number: String, sourceBase: String): String {
    var result = BigDecimal.ZERO
    val baseBig = sourceBase.toBigDecimal()
    for (idx in number.indices) {
        val sym = number[idx]
        val num = baseSym.indexOf(sym).toBigDecimal()
        val pp = baseBig.pow(idx + 1)
        val dd = num.divide(pp, RoundingMode.HALF_UP)
        result = result + num.divide(baseBig.pow(idx + 1), 5, RoundingMode.HALF_UP)
    }
    return result.toString().drop(2)
}

/////////////////////////////////////////////////////////////////////////
//From decimal to any base
fun DecToAnyBase(number: String, targetBase: String): String {
    val parts = number.split(".")
    var result = IntDecToAnyBase(parts[0], targetBase)
    if (parts.size > 1 && parts[1].isNotEmpty()) {
        result = "$result.${FractalDecToAnyBase(parts[1], targetBase)}"
    }
    return result
}

fun IntDecToAnyBase(number: String, targetBase: String): String {
    var result = ""
    var quotient = number.toBigInteger()
    val baseBig = targetBase.toBigInteger()
    do {
        val remainder = quotient % baseBig
        quotient = quotient / baseBig
        result += baseSym[remainder.toInt()]
    } while (quotient > BigInteger.ZERO)
    return result.reversed()
}

fun FractalDecToAnyBase(number: String, targetBase: String): String {
    var result = ""
    var remainder = ("0." + number).toBigDecimal()
    val baseBig = targetBase.toBigDecimal()
    do {
        val mult = remainder * baseBig
        result += baseSym[mult.toInt()]
        remainder = mult.remainder(BigDecimal.ONE)
    } while (remainder > BigDecimal.ZERO && result.length < ROUND_LIMIT)
    return result
}