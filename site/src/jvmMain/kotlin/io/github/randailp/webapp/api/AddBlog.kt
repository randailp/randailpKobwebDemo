package io.github.randailp.webapp.api

import BlogPostBody
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.readBodyText
import io.github.randailp.webapp.api.db.Database
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Api
fun addBlog(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.POST) return

    val blogContent = ctx.req.readBodyText()?.let { Json.parseToJsonElement(it) }

    blogContent?.let { content ->
        ctx.data.getValue<Database>().addBlog(
            BlogPostBody(
                content = content.jsonObject.getValue("content").toString(),
                postDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            )
        )
    }

    ctx.res.status = 200
}