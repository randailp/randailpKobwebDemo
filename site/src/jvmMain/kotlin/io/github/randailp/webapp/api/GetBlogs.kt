package io.github.randailp.webapp.api

import ApiResponse
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import io.github.randailp.webapp.api.db.Database
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Exception

@Api
suspend fun getBlogs(ctx:ApiContext){
    try{
        val id = ctx.req.params.getValue("id")
        val blogs = ctx.data.getValue<Database>().getBlogs()
        val selectedBlog = blogs.first {
            it.id == id
        }
        ctx.res.setBodyText(
            Json.encodeToString<ApiResponse>(
                value = ApiResponse.Success(data = selectedBlog)
            )
        )
    }catch(e: Exception){
        ctx.res.setBodyText(
            Json.encodeToString(
                ApiResponse.Error(errorMessage = e.message.toString())
            )
        )
    }
}