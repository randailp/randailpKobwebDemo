package io.github.randailp.webapp.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.silk.components.graphics.Image
import org.jetbrains.compose.web.css.CSSSizeValue
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.cssRem

@Composable
fun ClickableIcon(
    iconSize: CSSSizeValue<CSSUnit.rem> = 10.cssRem,
    ctx: PageContext,
    imageSrc: String,
    navSrc: String
) {
    var color by remember {
        mutableStateOf(Color.antiquewhite)
    }
    Image(
        src = imageSrc,
        modifier = Modifier.backgroundColor(color).onMouseOver {
            color = Color.azure
        }.onClick {
            ctx.router.navigateTo(
                pathQueryAndFragment = navSrc,
                openExternalLinksStrategy = OpenLinkStrategy.IN_NEW_TAB
            )
        }.onMouseOut {
            color = Color.antiquewhite
        }.size(iconSize)
    )
}