import java.math.BigInteger
fun main() {
    // write your code here
    val bigA = readln().toBigInteger()
    val bigB = readln().toBigInteger()
    println((bigA + bigB + (bigA - bigB).abs()) / BigInteger.TWO)
}
