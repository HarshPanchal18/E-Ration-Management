package org.harsh

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties

fun main() {
    connection()
}

fun connection() {
    val userInfo = Properties().apply {
        put("user", "root")
        put("password", "")
    }

    val dbUrl = "jdbc:mysql://localhost:3306/jdbcDemo"
    val connection: Connection

    try {
        Class.forName("com.mysql.cj.jdbc.Driver")
        connection = DriverManager.getConnection(dbUrl, userInfo)

        val statement = connection.createStatement()
        val selectQuery = "Select * from student"
        val resultSet = statement.executeQuery(selectQuery)

        while (resultSet.next()) {
            println(
                "${resultSet.getString(1)} " +
                        "${resultSet.getString(2).substring(0..2)} " +
                        "${resultSet.getString(3)} "
            )
        }

    } catch (e: SQLException) {
        throw RuntimeException(e)
    }

    connection.close()
}