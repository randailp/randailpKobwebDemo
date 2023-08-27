package io.github.randailp.webapp.pages

import BlogApiResponse
import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.core.Page
import io.github.randailp.webapp.components.layouts.PageLayout
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import parseAsString

@Page
@Composable
fun BlogsPage() {
    var apiResponseState by remember { mutableStateOf("") }
    PageLayout(title = "BLOGS") {
        Column {
            LaunchedEffect(Unit){
                apiResponseState = fetchBlogs().parseAsString()
            }
            P{
                Text(value= apiResponseState)
            }
        }
    }
}

private suspend fun fetchBlogs() : BlogApiResponse {
    val result = window.api.tryGet(apiPath = "getblogs")?.decodeToString()
    return Json.decodeFromString(string = result.toString())
}