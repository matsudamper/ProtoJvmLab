package net.matsudamper.client

import io.grpc.ManagedChannelBuilder
import net.matsudamper.MainGrpc
import net.matsudamper.MainOuterClass
import java.util.concurrent.TimeUnit

class Client(host: String, port: Int) {
    private val channel = ManagedChannelBuilder
        .forAddress(host, port)
        .idleTimeout(150, TimeUnit.SECONDS)
        .usePlaintext()
        .build()

    fun start() {
        println("call client")
        val stub = MainGrpc.newBlockingStub(channel)

        val response = runCatching {
            stub.test(
                MainOuterClass.ClientRequest1.newBuilder()
                    .setOne("ClientRequest1 One")
                    .setTwo("ClientRequest1 Two")
                    .build()
            )
        }.onFailure {
            it.printStackTrace()
        }.getOrThrow()
        println("value=${response.value}")
    }
}