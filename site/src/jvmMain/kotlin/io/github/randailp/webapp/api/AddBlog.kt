package io.github.randailp.webapp.api

import Blog
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.readBodyText
import io.github.randailp.webapp.api.db.Database

@Api
fun addBlog(ctx:ApiContext){
    if(ctx.req.method != HttpMethod.POST) return

    val blogId = ctx.req.params["id"]
    val blogContent = ctx.req.readBodyText()

    blogId?.let{id ->
        blogContent?.let{content ->
            ctx.data.getValue<Database>().addBlog(Blog(content, id))
        }
    }
    ctx.res.status = 200
}