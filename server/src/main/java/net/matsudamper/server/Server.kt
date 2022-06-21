package net.matsudamper.server

import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.stub.StreamObserver
import net.matsudamper.MainGrpc
import net.matsudamper.MainOuterClass

class Server(private val port: Int) {
    object Service : MainGrpc.MainImplBase() {
        override fun test(
            request: MainOuterClass.Request1,
            responseObserver: StreamObserver<MainOuterClass.Response>
        ) {
            println("call test")
            responseObserver.onNext(
                MainOuterClass.Response.newBuilder()
                    .setValue("one=${request.one}")
                    .build()
            )
            responseObserver.onCompleted()
        }
    }

    fun start() {
        println("start server")
        val server = ServerBuilder.forPort(port)
            .addService(Service)
            .addService(ProtoReflectionService.newInstance())
            .build()

        server.start()
        server.awaitTermination()
    }
}