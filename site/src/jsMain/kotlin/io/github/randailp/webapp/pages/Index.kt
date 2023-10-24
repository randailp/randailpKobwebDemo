package io.github.randailp.webapp.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.TextInput
import io.github.randailp.webapp.components.layouts.PageLayout
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    PageLayout("randailp's corner") {
        Text("This website is a test for development with Kobweb.")
    }
}
