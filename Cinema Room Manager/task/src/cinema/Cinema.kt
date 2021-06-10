package cinema

import java.lang.ArithmeticException
import java.lang.NumberFormatException

fun main() {

    var rows = 0
    var seats = 0

    var rightInput = false
    while (!rightInput) {
        try {
            println("Enter the number of rows:")
            val rowsInput = readLine()!!
            println("Enter the number of seats in each row:")
            val seatsInput = readLine()!!
            rows = rowsInput.toInt()
            seats = seatsInput.toInt()
            rightInput = true
        } catch (e: NumberFormatException) {
            println("Wrong input")
        }
    }

    val totalSeats = rows * seats
    val seatsMap = Array(rows) { CharArray(seats) { 'S' } }
    val income = if (totalSeats <= 60) {
        totalSeats * 10
    } else {
        val front = rows / 2
        front * seats * 10 + (rows - front) * seats * 8
    }

    var numberOfTickets = 0
    var currentIncome = 0
    var nextStep = true
    while(nextStep) {
        println(
            """
                
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit        
    """.trimIndent()
        )

        val choice = try {
            readLine()!!.toInt()
        } catch (e: NumberFormatException) {
            5
        }
        when (choice) {

            1 -> printMap(rows, seats, seatsMap)

            2 -> {
                rightInput = false
                while (!rightInput) {
                    try {
                        println("Enter a row number:")
                        val rowInput = readLine()!!
                        println("Enter a seat number in that row:")
                        val seatInput = readLine()!!
                        val row = rowInput.toInt()
                        val seat = seatInput.toInt()

                        if (seatsMap[row - 1][seat - 1] == 'B') throw Exception()
                        seatsMap[row - 1][seat - 1] = 'B'
                        val front = rows / 2
                        val ticket = if (totalSeats <= 60 || row <= front) {
                            10
                        } else 8
                        println("\nTicket price: \$$ticket")
                        numberOfTickets++
                        currentIncome += ticket
                        rightInput = true
                    } catch (e: NumberFormatException) {
                        println("Wrong input")
                    } catch (e: IndexOutOfBoundsException) {
                        println("Wrong input!")
                    } catch (e: Exception) {
                        println("That ticket has already been purchased!")
                    }
                }
            }

            3 -> {
                val percent = try {
                    (numberOfTickets.toDouble() / totalSeats.toDouble()) * 100
                } catch (e: ArithmeticException) {
                    0.00
                }
                println("""
                    
                    Number of purchased tickets: $numberOfTickets
                    Percentage: ${"%.2f".format(percent)}%
                    Current income: $$currentIncome
                    Total income: $$income
                    
                """.trimIndent())
            }

            0 -> nextStep = false
        }
    }
}

fun printMap (rows: Int, seats: Int, seatsMap: Array<CharArray>) {
    println("\nCinema:")
    for (i in 1..rows) {
        if (i == 1) {
            print(" ")
            for (j in 1..seats) {
                print(" $j")
            }
        }
        print("\n$i ")
        print(seatsMap[i - 1].joinToString(separator = " "))
    }
    println()
}