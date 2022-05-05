fun main() {
    val (a, b, c, d) = MutableList(4) { readln().toBigInteger() }
    println("${ (-a) * b + c -d }")
}
