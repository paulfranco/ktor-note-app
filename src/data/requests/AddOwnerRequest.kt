package co.paulfran.data.requests

data class AddOwnerRequest(
    val noteId: String,
    val owner: String
)