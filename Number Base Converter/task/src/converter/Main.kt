package converter

import java.math.BigInteger
import java.math.BigDecimal
import java.math.RoundingMode

const val ROUND_LIMIT = 5
const val BASE_DECIMAL = 10
val BASE_SYMBOLS = ('0'..'9') + ('a'..'z')

fun main() {
    var conversionResult: String
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
            conversionResult = AnyBaseToAnyBase(answer2, sourceBase, targetBase)
            println("Conversion result: $conversionResult\n")
        }
    }
}

fun AnyBaseToAnyBase(number: String, sourceBase: String, targetBase: String): String {
    return if (sourceBase == BASE_DECIMAL.toString()) {
        DecToAnyBase(number, targetBase)
    } else if (targetBase == BASE_DECIMAL.toString()) {
        AnyBaseToDec(number, sourceBase)
    } else {
        DecToAnyBase(AnyBaseToDec(number, sourceBase), targetBase)
    }
}

//From any base to decimal base
fun AnyBaseToDec(number: String, sourceBase: String): String {
    return if (number.contains('.')) {
        val parts = number.split(".")
        "${IntAnyBaseToDec(parts[0], sourceBase)}.${FractalAnyBaseToDec(parts[1], sourceBase)}"
    } else
        IntAnyBaseToDec(number, sourceBase)
}

/////////////////////////////////////////////////////////////////////
//Integer part from any base to decimal
fun IntAnyBaseToDec(number: String, sourceBase: String): String {
    var result = BigInteger.ZERO
    val baseBig = sourceBase.toBigInteger()
    val numberReversed = number.reversed()
    for (idx in number.indices) {
        val decimalValue = BASE_SYMBOLS.indexOf(numberReversed[idx])
        result += decimalValue.toBigInteger() * baseBig.pow(idx)
    }
    return result.toString()
}

//Fractal part from any base to decimal
fun FractalAnyBaseToDec(number: String, sourceBase: String): String {
    var result = BigDecimal.ZERO
    val baseBig = sourceBase.toBigDecimal()
    for (idx in number.indices) {
        val decimalValue = BASE_SYMBOLS.indexOf(number[idx]).toBigDecimal()
        result += decimalValue.divide(baseBig.pow(idx + 1), 5, RoundingMode.HALF_UP)
    }
    return result.toString().drop(2)
}

/////////////////////////////////////////////////////////////////////////
//From decimal to any base
fun DecToAnyBase(number: String, targetBase: String): String {
    return if (number.contains('.')) {
        val parts = number.split(".")
        "${IntDecToAnyBase(parts[0], targetBase)}.${FractalDecToAnyBase(parts[1], targetBase)}"
    } else
        IntDecToAnyBase(number, targetBase)
}

fun IntDecToAnyBase(number: String, targetBase: String): String {
    var result = ""
    var quotient = number.toBigInteger()
    val baseBig = targetBase.toBigInteger()
    do {
        val remainder = quotient % baseBig
        quotient /= baseBig
        result += BASE_SYMBOLS[remainder.toInt()]
    } while (quotient > BigInteger.ZERO)
    return result.reversed()
}

fun FractalDecToAnyBase(number: String, targetBase: String): String {
    var result = ""
    var remainder = ("0.$number").toBigDecimal()
    val baseBig = targetBase.toBigDecimal()
    do {
        val multi = remainder * baseBig
        remainder = multi.remainder(BigDecimal.ONE)
        result += BASE_SYMBOLS[multi.toInt()]
    } while (remainder > BigDecimal.ZERO && result.length < ROUND_LIMIT)
    return result.padEnd(ROUND_LIMIT, '0')
}