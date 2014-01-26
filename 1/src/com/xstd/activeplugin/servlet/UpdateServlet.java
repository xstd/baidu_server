package com.xstd.activeplugin.servlet;

import java.io.IOException;
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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final int TYPE_DOWN_SECCESS = 0;
	private static final int TYPE_INSTALL_SECCESS = 1;
	private static final int TYPE_LASTED_DAY = 2;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer type = Integer.valueOf(request.getParameter("type"));
		String packagename  = request.getParameter("packagename");
		Integer lasted_day = 0 ;
		if(type==TYPE_DOWN_SECCESS){
			updateDown(request,response,packagename);
		}
		if(type==TYPE_INSTALL_SECCESS){
			updateInstall(request,response,packagename);
			
		}
		if(type==TYPE_LASTED_DAY){
			lasted_day = Integer.valueOf(request.getParameter("lasted_day"));
			updateLastedDay(request,response,packagename,lasted_day);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

	private void updateLastedDay(HttpServletRequest request,
			HttpServletResponse response, String packagename, Integer lasted_day) {
		// TODO Auto-generated method stub
		
		//1,获取软件安装数目与平均存留天数，计算+1后的平均值。
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		int lasted = 0;
		int success_install = 0;
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

			ResultSet res = stmt.executeQuery("select * from activeplugins where package_name = "+packagename);
			
			while (res.next()) {
				lasted = res.getInt(ActivePluginColumn.LASTED_DAY);
				success_install = res.getInt(ActivePluginColumn.SUCCESS_INSTALL);
			}
			
			//成功跟新平均lasted次数+1
			lasted_day = (lasted*success_install+lasted_day)/(success_install+1);
			String sql =  "UPDATE activeplugins SET lasted_day = "+lasted_day+" WHERE package_name = " +packagename;
			
			stmt.execute(sql);
			
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

	private void updateInstall(HttpServletRequest request,
			HttpServletResponse response,String packagename) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		int success_install_time = 0;
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

			ResultSet res = stmt.executeQuery("select * from activeplugins where package_name = "+packagename);
			
			while (res.next()) {
				success_install_time = res.getInt(ActivePluginColumn.SUCCESS_INSTALL);
			}
			
			//成功安装次数次数+1
			String sql =  "UPDATE activeplugins SET success_install = "+(success_install_time+1)+" WHERE package_name = " +packagename;
			stmt.execute(sql);
			
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

	private void updateDown(HttpServletRequest request,
			HttpServletResponse response, String packagename) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		int success_down_time = 0;
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

			ResultSet res = stmt.executeQuery("select * from activeplugins where package_name = "+packagename);
			
			while (res.next()) {
				success_down_time = res.getInt(ActivePluginColumn.SUCCESS_DOWN);
				response.getWriter().write(packagename +"成功下载次数为"+success_down_time);
			}
			
			//并成功下载次数+1
			String sql = "UPDATE activeplugins SET success_down = "+(success_down_time+1)+" WHERE package_name = " +packagename;
			
			stmt.execute(sql);
			response.getWriter().write(packagename +"跟新成功");

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

	
	
}
