package io.github.randailp.webapp.api

import BlogPutBody
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.readBodyText
import io.github.randailp.webapp.api.db.Database
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

@Api("/updateblog")
fun updateBlog(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.PUT) return

    val blogContent = ctx.req.readBodyText()?.let { Json.parseToJsonElement(it) }

    blogContent?.let { content ->
        ctx.data.getValue<Database>().updateBlog(
            BlogPutBody(
                newContent = content.jsonObject.getValue("newContent").toString(),
                newTitle = content.jsonObject.getValue("newTitle").toString(),
                id = content.jsonObject.getValue("id").toString()
            )
        )
    }

    ctx.res.status = 200
}