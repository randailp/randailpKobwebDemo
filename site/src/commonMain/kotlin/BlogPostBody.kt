import kotlinx.serialization.Serializable

@Serializable
data class BlogPostBody(
    val content: String
)

