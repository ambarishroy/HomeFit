package ie.setu.homefit.firebase.auth

sealed class Response<out R> {
    data class Success<out R>(val data: R) : Response<R>()
    data class Failure(val e: Exception) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
