package net.matsudamper.client

fun main(args: Array<String>) {
    val port = 8080
    Client(host = "localhost", port = port).start()
}