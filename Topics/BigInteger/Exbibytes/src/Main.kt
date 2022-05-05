import java.math.BigInteger
const val POW_EXBIBYTE = 63
fun main() {
    // write your code here
    val number = readln().toBigInteger()
    println(BigInteger.valueOf(2).pow(POW_EXBIBYTE) * number)
}
