package io.github.randailp.webapp.api.db

import Blog
import BlogPostBody
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.postgresql.Driver
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*


class Database {

    private val jdbcUrl: String = System.getenv("JDBC_URL")
    private val dbPass: String = System.getenv("DB_PASS")
    private val dbUser: String = System.getenv("DB_USER")

    private val hikariConfig = HikariConfig()

    private val connectionPool = buildConnectionPool()
    private fun buildConnectionPool(): Connection{
        hikariConfig.jdbcUrl = jdbcUrl
        hikariConfig.username = dbUser
        hikariConfig.password = dbPass
        hikariConfig.setDriverClassName(Driver::class.java.getName())

        val dataSource = HikariDataSource(hikariConfig)
        return dataSource.connection
    }

    fun getBlogs(): List<Blog> {
        val connect = connectionPool
        val mutableList = mutableListOf<Blog>()
        try {
            val st = connect.createStatement()
            val rs = st?.executeQuery("SELECT * FROM Blogs")
            while (rs?.next() == true) {
                mutableList.add(
                    Blog(
                        content = rs.getString("content"),
                        id = rs.getString("id"),
                        postDate = rs.getString("postDate")
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

    fun addBlog(blogPostBody: BlogPostBody) {
        val connect = connectionPool
        try {
            val insertSQL = "INSERT INTO Blogs" +
                    " (content, id, postDate) VALUES " +
                    " (?, ?, ?);"
            val prepareStatement = connect.prepareStatement(insertSQL)

            prepareStatement?.setString(1, blogPostBody.content)
            prepareStatement?.setString(2, UUID.randomUUID().toString())
            prepareStatement?.setString(3, blogPostBody.postDate)

            prepareStatement?.executeUpdate()

            prepareStatement?.close()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}

@InitApi
fun initDatabase(ctx: InitApiContext) {
    ctx.data.add(Database())
}

//fun main() {
//    val jdbcUrl = System.getenv("JDBC_URL")
//    val dbPass = System.getenv("DB_PASS")
//    val dbUser = System.getenv("DB_USER")
//    val connect = DriverManager.getConnection(jdbcUrl, dbUser, dbPass)
//    val mutableList = mutableListOf<Blog>()
//    try {
//        val st = connect.createStatement()
//        val alterSQL = "ALTER TABLE Blogs " +
//                "ADD COLUMN postDate VARCHAR(255);"
//        val rs = st?.executeQuery(alterSQL)
//        while (rs?.next() == true) {
//            println(rs.getString("content"))
//            mutableList.add(
//                Blog(
//                    content = rs.getString("content"),
//                    id = rs.getString("id"),
//                    postDate = rs.getString("postDate")
//                )
//            )
//        }
//        st?.close()
//        rs?.close()
//    } catch (e: SQLException) {
//        println(e.message)
//    }
//    println(mutableList)
//}

//fun main(){
//    val updateSQL = "INSERT INTO Blogs" +
//            " (content, id) VALUES " +
//            " (?, ?);"
//    val prepareStatement = connect.prepareStatement(updateSQL)
//
//    prepareStatement?.setString(1, "hello")
//    prepareStatement?.setString(2, "1")
//
//    prepareStatement?.executeUpdate()
//}