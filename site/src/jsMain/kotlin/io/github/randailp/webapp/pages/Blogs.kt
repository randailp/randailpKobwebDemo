package io.github.randailp.webapp.pages

import BlogApiResponse
import BlogPostBody
import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import io.github.randailp.webapp.components.layouts.PageLayout
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import parseAsString

@Page
@Composable
fun BlogsPage() {
    val ctx = rememberPageContext()
    var apiResponseState by remember { mutableStateOf("") }
    PageLayout(title = "BLOGS") {
        Column {
            LaunchedEffect(Unit) {
                apiResponseState = fetchBlogs().parseAsString()
            }
            P {
                Text(value = apiResponseState)
            }
            Button(attrs = Modifier.onClick {
                ctx.router.navigateTo("/blogs/make-blog")
            }.toAttrs()) {
                SpanText("make post")
            }
        }
    }
}

@Page("/blogs/make-blog")
@Composable
fun MakeBlogPage(){
    val ctx = rememberPageContext()
    val scope = rememberCoroutineScope()
    var inputText by remember { mutableStateOf("") }
    PageLayout(title = "MAKE BLOG POST"){
        Column {
            Input(
                type = InputType.Text
            ){
                this.onChange {
                    inputText = it.value
                }
            }
            Button(attrs = Modifier.onClick {
                scope.launch{
                    val apiResponse = postBlog(inputText)
                    if(apiResponse is BlogApiResponse.Success){
                        ctx.router.navigateTo("/blogs")
                    }
                }
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

private suspend fun postBlog(
    content: String
): BlogApiResponse {
    val blogPostBodyByteArray = Json.encodeToString(BlogPostBody(content = content)).encodeToByteArray()
    val results = window.api.tryPost(apiPath = "addblog", body = blogPostBodyByteArray)?.decodeToString()
    return Json.decodeFromString(string = results.toString())
}