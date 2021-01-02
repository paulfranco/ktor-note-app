package co.paulfran.routes

import co.paulfran.data.checkIfUserExists
import co.paulfran.data.checkPasswordForEmail
import co.paulfran.data.collections.User
import co.paulfran.data.registerUser
import co.paulfran.data.requests.AccountRequest
import co.paulfran.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.loginRoute() {
    route("/login") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch(e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }
            val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)
            if (isPasswordCorrect) {
                call.respond(OK, SimpleResponse(true, "You are now logged in"))
            } else {
                call.respond(OK, SimpleResponse(false, "Incorrect credentials"))
            }
        }
    }
}