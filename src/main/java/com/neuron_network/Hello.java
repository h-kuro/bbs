package com.neuron_network;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.SQLException;

public class Hello extends HttpServlet {
    private DataSource dataSource = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        Connection conn;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Context context = new InitialContext();
            this.dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");
            conn = dataSource.getConnection();
            printWriter.println("con: " + conn.getMetaData().getURL());
        } catch (NamingException | ClassNotFoundException | SQLException e) {
            printWriter.println("<p>" + e.toString() + "</p>");
            throw new ServerException(e.toString());
        }

        printWriter.flush();
        printWriter.close();
    }
}