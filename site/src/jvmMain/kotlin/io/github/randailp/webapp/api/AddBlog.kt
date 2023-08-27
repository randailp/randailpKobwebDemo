package io.github.randailp.webapp.api

import BlogPostBody
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.readBodyText
import io.github.randailp.webapp.api.db.Database

@Api
fun addBlog(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.POST) return

    val blogContent = ctx.req.readBodyText()

    blogContent?.let { content ->
        ctx.data.getValue<Database>().addBlog(
            BlogPostBody(
                content = content
            )
        )
    }

    ctx.res.status = 200
}