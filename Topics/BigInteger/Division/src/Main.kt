fun main() {
    val (a, b) = MutableList(2) { readln().toBigInteger() }
    println("$a = $b * ${a / b } + ${a % b }")    
}
