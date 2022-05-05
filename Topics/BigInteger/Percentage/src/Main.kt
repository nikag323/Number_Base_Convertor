import java.math.BigInteger
fun main() {
    val bigA = readln().toBigInteger()
    val bigB = readln().toBigInteger()
    val sum = bigA + bigB
    val percentA = bigA * BigInteger.valueOf(100) / sum
    val percentB = bigB * BigInteger.valueOf(100) / sum
    println("$percentA% $percentB%")
}
