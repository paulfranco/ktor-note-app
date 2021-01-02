package co.paulfran.routes

import co.paulfran.data.checkIfUserExists
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

fun Route.registerRoute() {
    route("/register") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch(e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }
            val userExists = checkIfUserExists(request.email)
            if (!userExists) {
                if (registerUser(User(request.email, request.password))) {
                    call.respond(OK, SimpleResponse(true, "Successfully Created Account"))
                } else {
                    call.respond(OK, SimpleResponse(false, "An Unknown error occurred"))
                }
            } else {
                call.respond(OK, SimpleResponse(false, "User with that email already exits"))
            }
        }
    }
}