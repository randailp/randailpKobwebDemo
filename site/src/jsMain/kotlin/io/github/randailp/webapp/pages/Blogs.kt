package io.github.randailp.webapp.pages

import Blog
import BlogApiResponse
import BlogPostBody
import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.resize
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import io.github.randailp.webapp.components.layouts.PageLayout
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.cols
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.rows
import org.jetbrains.compose.web.dom.*

@Page
@Composable
fun BlogsPage() {
    val ctx = rememberPageContext()
    var blogObjectState by remember { mutableStateOf(listOf<Blog>()) }
    LaunchedEffect(Unit) {
        blogObjectState = (fetchBlogs() as BlogApiResponse.Success).data
    }
    PageLayout(title = "BLOGS") {
        Column(modifier = Modifier.scrollBehavior(ScrollBehavior.Auto)) {
            H2{
                blogObjectState.forEach {
                    Link(
                        text = it.title.toString().removeSurrounding("\""),
                        path = "/blogs/${it.id}"
                        )
                    Br()
                }
            }
            Button(attrs = Modifier.onClick {
                ctx.router.navigateTo("/blogs/make-blog")
            }.toAttrs()) {
                SpanText("make post")
            }
        }
    }
}

@Page("/blogs/{id}")
@Composable
fun BlogDetailsPage(){
    val ctx = rememberPageContext()
    val blogId = ctx.route.params.getValue("id")
    var blogObjectState by remember { mutableStateOf(listOf<Blog>()) }
    var currentBlogState by remember { mutableStateOf(Blog()) }
    LaunchedEffect(Unit){
        blogObjectState = (fetchBlogById(blogId) as BlogApiResponse.Success).data
        currentBlogState = blogObjectState.find {
            it.id == blogId
        } ?: Blog()
    }
    PageLayout(title = currentBlogState.title?.removeSurrounding("\"") ?: ""){
        H2{
            SpanText(text = currentBlogState.content?.removeSurrounding("\"") ?: "")
        }
    }
}

@Page("/blogs/make-blog")
@Composable
fun MakeBlogPage() {
    val scope = rememberCoroutineScope()
    var bodyText by remember { mutableStateOf("") }
    var titleText by remember { mutableStateOf("") }
    PageLayout(title = "MAKE BLOG POST") {
        Column {
            Input(
                type = InputType.Text,
                attrs = Modifier.toAttrs(finalHandler = {
                    this.onChange {
                        titleText = it.value
                    }
                    this.placeholder("Enter blog title here")
                })
            )
            TextArea(
                attrs = Modifier.toAttrs(finalHandler = {
                    this.rows(5)
                    this.cols(40)
                    this.style {
                        this.resize(Resize.None)
                    }
                    this.onChange {
                        bodyText = it.value
                    }
                    this.placeholder("Write your thoughts here")
                })
            )
            Button(attrs = Modifier.onClick {
                scope.launch {
                    if (bodyText.isNotEmpty() && titleText.isNotEmpty()) {
                        postBlog(bodyText, titleText)
                    }
                }
                window.location.replace("/blogs")
            }.toAttrs()) {
                SpanText("make post")
            }
        }
    }
}

private suspend fun fetchBlogs(): BlogApiResponse {
    val result = window.api.tryGet(apiPath = "getblogs")?.decodeToString()
    return Json.decodeFromString(string = result.toString())
}

private suspend fun fetchBlogById(
    id: String
): BlogApiResponse {
    val result = window.api.tryGet(apiPath = "getblogs?id=$id")?.decodeToString()
    return Json.decodeFromString(string = result.toString())
}

private suspend fun postBlog(
    content: String,
    title: String,
): BlogApiResponse {
    val blogPostBodyByteArray = Json.encodeToString(BlogPostBody(content = content, title = title)).encodeToByteArray()
    val results = window.api.tryPost(apiPath = "addblog", body = blogPostBodyByteArray)?.decodeToString()
    return Json.decodeFromString(string = results.toString())
}