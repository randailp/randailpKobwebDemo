import kotlinx.serialization.Serializable

@Serializable
data class Blog(
    val content: String,
    val id: String,
)
