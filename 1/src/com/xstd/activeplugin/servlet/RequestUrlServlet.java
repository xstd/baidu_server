package com.xstd.activeplugin.servlet;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xstd.activeplugin.data.ActivePluginColumn;

/**
 * Servlet implementation class SeccessDownLoadServlet
 */
@WebServlet("/SeccessDownLoadServlet")
public class RequestUrlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestUrlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer id =  Integer.valueOf(request.getParameter("id"));
		
		// 统计url
		
		updateRequestTime(request,response,id);
	}

	private void updateRequestTime(HttpServletRequest request,
			HttpServletResponse response,int id) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String url = "";
		int quest_time = 0;
		try {
			/***** 1. 填写数据库相关信息(请查找数据库详情页) *****/
			String databaseName = " XOtaEYmhZZVwVbGuQRIl ";
			String host = "sqld.duapp.com";
			String port = "4050";
			String username = " Fv5gpqojQ1SfCX5jbWHCWmEY";// 用户名(api key);
			String password = "yDaGjxCpjgGMaxkQDwwFj7D84SU4XSvP";// 密码(secret
																	// key)
			String driverName = "com.mysql.jdbc.Driver";
			String dbUrl = "jdbc:mysql://";
			String serverName = host + ":" + port + "/";
			String connName = dbUrl + serverName + databaseName;

			/****** 2. 接着连接并选择数据库名为databaseName的服务器 ******/
			Class.forName(driverName);
			connection = DriverManager.getConnection(connName, username,
					password);
			stmt = connection.createStatement();
			/* 至此连接已完全建立，就可对当前数据库进行相应的操作了 */
			/* 3. 接下来就可以使用其它标准mysql函数操作进行数据库操作 */
			// 创建一个数据库表

			ResultSet res = stmt.executeQuery("select * from activeplugins where id = "+id);
			
			while (res.next()) {
				quest_time = res.getInt(ActivePluginColumn.QUEST_TIME);
				url = res.getString(ActivePluginColumn.APK_URI);
			}
			
			//成功请求次数次数+1
			String sql1 = "UPDATE activeplugins SET quest_time="+(quest_time+1)+" WHERE id = " +id;
			stmt.execute(sql1);
			
			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
