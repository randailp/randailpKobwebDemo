import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse {
    @Serializable
    @SerialName("idle")
    object Idle: ApiResponse()
    @Serializable
    @SerialName("success")
    data class Success(val data: Blog): ApiResponse()
    @Serializable
    @SerialName("error")
    data class Error(val errorMessage: String): ApiResponse()
}

fun ApiResponse.parseAsString(): String{
    return when(this){
        is ApiResponse.Idle -> {
            ""
        }
        is ApiResponse.Success -> {
            this.data.content
        }
        is ApiResponse.Error -> {
            this.errorMessage
        }
    }
}