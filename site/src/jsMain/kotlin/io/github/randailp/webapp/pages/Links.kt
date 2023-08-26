package io.github.randailp.webapp.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.silk.components.graphics.Image
import io.github.randailp.webapp.components.layouts.PageLayout
import io.github.randailp.webapp.components.widgets.ClickableIcon
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun LinksPage() {
    val ctx = rememberPageContext()
    PageLayout(title = "LINKS") {
        H1 {
            Text("randailp")
        }
        Row {
            //Github
            ClickableIcon(
                ctx = ctx,
                imageSrc = "https://cdn-icons-png.flaticon.com/512/25/25231.png",
                navSrc = "https://www.github.com/randailp"
            )
            //Twitter
            ClickableIcon(
                ctx = ctx,
                imageSrc = "https://png.pngtree.com/png-vector/20221018/ourmid/pngtree-twitter-social-media-round-icon-png-image_6315985.png",
                navSrc = "https://www.twitter.com/randailp"
            )
            //Reddit
            ClickableIcon(
                ctx = ctx,
                imageSrc = "https://upload.wikimedia.org/wikipedia/en/thumb/b/bd/Reddit_Logo_Icon.svg/1200px-Reddit_Logo_Icon.svg.png",
                navSrc = "https://www.reddit.com/user/seko78"
            )
        }
    }
}