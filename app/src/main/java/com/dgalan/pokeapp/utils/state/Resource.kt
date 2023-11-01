package com.dgalan.pokeapp.utils.state

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception, val errorMessage: String? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data object Idle : Resource<Nothing>()

    fun <R> map(transform: (T) -> R): Resource<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> Error(exception, errorMessage)
            is Loading -> Loading
            is Idle -> Idle
        }
    }

    fun <T, R> T.runCatchingResource(block: T.() -> R): Resource<R> {
        return try {
            Success(block())
        } catch (e: Exception) {
            Error(e, e.message)
        }
    }
}
