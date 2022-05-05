package converter

const val baseSym = "0123456789ABCDEF"

fun main() {
    println("Hello, world!")
    while (true) {
        println("Do you want to convert /from decimal or /to decimal? (To quit type /exit)")
        var command = readln()
        when (command) {
            "/from" -> {
                fromDec()
            }
            "/to" -> {
                toDec()
            }
            "/exit" -> {
                return
            }
        }
    }
}

fun toDec() {
    print("Enter source number: ")
    var number = readln().uppercase()
    print("Enter source base: ")
    val base = readln().toInt()
    var result = 0
    for (sym in number) {
        val idx = baseSym.indexOf(sym)
        result = base * result + idx
    }
    println("Conversion to decimal result: $result")
}


 fun fromDec() {
    print("Enter number in decimal system: ")
    var number = readln().toInt()
    print("Enter target base: ")
    val base = readln().toInt()
    var result = ""
    do{
        result += baseSym[number % base]
        number /= base
    }while (number > 0)
    result = result.reversed()
    println("Conversion result: $result")
}
