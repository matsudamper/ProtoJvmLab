import com.google.protobuf.gradle.*

plugins {
    kotlin("jvm") version "1.6.21"
    id("com.google.protobuf") version "0.8.18"
    `java-library`
}

group = "net.matsudamper.server"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        proto {
            srcDir("src/proto")
        }
        java {
            srcDirs(
                "build/generated/source/proto/main/grpckt",
                "build/generated/source/proto/main/grpc",
                "build/generated/source/proto/main/info",
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/kotlin",
            )
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.45.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.2.0:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    implementation("com.google.protobuf:protobuf-java:3.19.4")
    implementation("io.grpc:grpc-stub:1.45.1")
    implementation("io.grpc:grpc-protobuf:1.45.1")
    implementation("com.google.protobuf:protobuf-kotlin:3.19.4")
    implementation("io.grpc:grpc-services:1.46.0")
    implementation("io.grpc:grpc-kotlin-stub:1.2.1")
    implementation("io.grpc:grpc-netty-shaded:1.46.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.withType<GenerateProtoTask> {
    doFirst {
        file("$buildDir/generated/source/proto").deleteRecursively()
    }
}
