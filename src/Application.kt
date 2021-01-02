package co.paulfran

import co.paulfran.data.checkPasswordForEmail
import co.paulfran.routes.loginRoute
import co.paulfran.routes.noteRoutes
import co.paulfran.routes.registerRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // Optional Features
    install(DefaultHeaders)
    install(CallLogging)
    // Essential Features
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        configureAuth()
    }

    install(Routing) {
        registerRoute()
        loginRoute()
        noteRoutes()
    }
}

private fun Authentication.Configuration.configureAuth() {
    basic {
        realm = "Note Server"
        validate { credentials ->
            val email = credentials.name
            val password = credentials.password
            if (checkPasswordForEmail(email, password)) {
                UserIdPrincipal(email)
            } else null
        }
    }
}

