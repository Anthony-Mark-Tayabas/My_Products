package tayabas.anthony.myproducts.common

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkUtils {
    companion object {
        fun resolveError(e: Exception): State.ErrorState {
            var error = e

            when(e) {
                is SocketTimeoutException -> {
                    error = NetworkErrorException(errorMessage = "Connetion error")
                }
                is ConnectException -> {
                    error = NetworkErrorException(errorMessage = "No Internet Access")
                }
                is UnknownHostException -> {
                    error = NetworkErrorException(errorMessage = "Server is unavailable")
                }
            }

            if (e is HttpException) {
                when(e.code()) {
                    502 -> {
                        error = NetworkErrorException(e.code(), "Internal error")
                    }
                    401 -> {
                        throw AuthenticationException("Authentication Error")
                    }
                    400 -> {
                        error = NetworkErrorException.parseException(e)
                    }
                }
            }

            return State.ErrorState(error)
        }
    }
}