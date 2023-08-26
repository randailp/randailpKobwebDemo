package io.github.randailp.webapp.api.db

import Blog
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import java.sql.DriverManager
import java.sql.SQLException

class Database {
    private val jdbcUrl: String = System.getenv("JDBC_URL")
    private val dbPass: String = System.getenv("DB_PASS")
    private val dbUser: String = System.getenv("DB_USER")
    fun getBlogs(): List<Blog> {
        Class.forName("org.postgresql.Driver")
        val connect = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)
        val mutableList = mutableListOf<Blog>()
        try {
            val st = connect.createStatement()
            val rs = st?.executeQuery("SELECT * FROM Blogs")
            while (rs?.next() == true) {
                mutableList.add(
                    Blog(
                        content = rs.getString("content") ?: "",
                        id = rs.getString("id") ?: ""
                    )
                )
            }
            st?.close()
            rs?.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return mutableList
    }

    fun addBlog(blog: Blog) {
        Class.forName("org.postgresql.Driver")
        val connect = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)
        try {
            val updateSQL = "UPDATE Blogs " +
                    "SET content = ${blog.content} " +
                    "WHERE id = ${blog.id}"
            val prepareStatement = connect.prepareStatement(updateSQL)

            prepareStatement?.setString(1, blog.content)
            prepareStatement?.setString(2, blog.id)

            prepareStatement?.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}

@InitApi
fun initDatabase(ctx: InitApiContext) {
    ctx.data.add(Database())
}

fun main() {
    val jdbcUrl = System.getenv("JDBC_URL")
    val dbPass = System.getenv("DB_PASS")
    val dbUser = System.getenv("DB_USER")
    val connect = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)
    val mutableList = mutableListOf<Blog>()
    try {
        val st = connect.createStatement()
        val rs = st?.executeQuery("SELECT * FROM Blogs")
        while (rs?.next() == true) {
            println(rs.getString("content"))
            mutableList.add(
                Blog(
                    content = rs.getString("content") ?: "",
                    id = rs.getString("id") ?: ""
                )
            )
        }
        st?.close()
        rs?.close()
    } catch (e: SQLException) {
        println(e.message)
    }
    println(mutableList)
}