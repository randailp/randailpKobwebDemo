package io.github.randailp.webapp.pages

import ApiResponse
import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import io.github.randailp.webapp.components.layouts.PageLayout
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement
import parseAsString

@Page
@Composable
fun BlogsPage() {
    val scope = rememberCoroutineScope()
    var apiResponseState by remember { mutableStateOf("") }
    PageLayout(title = "BLOGS") {
        Column {
            SpanText(
                "Input blog id to see blog"
            )
            Input(
                type = InputType.Number,
                attrs = Modifier.toAttrs {
                    attr("placeholder", "Enter text")
                }
            )
            Button(attrs = Modifier.onClick {
                scope.launch {
                    val apiResponse = fetchBlogs()
                    apiResponseState = apiResponse.parseAsString()
                }
            }.toAttrs()) {
                SpanText("get blogs")
            }
            P{
                Text(value= apiResponseState)
            }
        }
    }
}

private suspend fun fetchBlogs() : ApiResponse {
    val inputText = (document.getElementById("id") as HTMLInputElement).value
    val result = window.api.tryGet(apiPath = "getblogs?id=$inputText")?.decodeToString()
    return Json.decodeFromString(string = result.toString())
}