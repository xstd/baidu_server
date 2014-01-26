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

import org.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.xstd.activeplugin.data.ActivePluginColumn;
import com.xstd.activeplugin.domain.FileInfo;

/**
 * Servlet implementation class FileUpLoadServlet
 */
@WebServlet("/FileUpLoadServlet")
public class DownLoadApkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ACCESSWAY = "http://activeplugin.duapp.com/bce_java_default/account";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownLoadApkServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String value = (String) request.getParameter("imei");
		if (value != null) {
			downloadWithAttr(request, response);
			return;
		} else {

			JSONArray jsonArray = null;
			Connection connection = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;
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

				ResultSet res = stmt
						.executeQuery("select * from activeplugins");

				jsonArray = new JSONArray();
				while (res.next()) {

					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(res.getInt(ActivePluginColumn.ID));
					fileInfo.setSoftware_name(res
							.getString(ActivePluginColumn.SOFTWARE_NAME));
					fileInfo.setPackage_name(res
							.getString(ActivePluginColumn.PACKAGE_NAME));
					fileInfo.setEveryday_count(res
							.getString(ActivePluginColumn.EVERYDAY_COUNT));
					fileInfo.setApk_uri(res
							.getString(ActivePluginColumn.APK_URI));

					JSONObject jsonObject = (JSONObject) JSONObject
							.toJSON(fileInfo);
					jsonArray.put(jsonObject);
				}

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
			if (jsonArray == null || jsonArray.length() == 0) {
				response.getWriter().write("");
			} else {
				response.getWriter().write(jsonArray.toString());
			}
		}
	}

	private void downloadWithAttr(HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray jsonArray = null;
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
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

			ResultSet res = stmt.executeQuery("select * from activeplugins");

			jsonArray = new JSONArray();
			while (res.next()) {

				FileInfo fileInfo = new FileInfo();
				fileInfo.setId(res.getInt(ActivePluginColumn.ID));
				fileInfo.setSoftware_name(res
						.getString(ActivePluginColumn.SOFTWARE_NAME));
				fileInfo.setPackage_name(res
						.getString(ActivePluginColumn.PACKAGE_NAME));
				fileInfo.setEveryday_count(res
						.getString(ActivePluginColumn.EVERYDAY_COUNT));
				fileInfo.setApk_uri(ACCESSWAY);

				JSONObject jsonObject = (JSONObject) JSONObject
						.toJSON(fileInfo);
				jsonArray.put(jsonObject);
			}

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
		if (jsonArray == null || jsonArray.length() == 0) {
			try {
				response.getWriter().write("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().write(jsonArray.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
