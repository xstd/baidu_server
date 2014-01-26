package com.xstd.activeplugin.servlet;

import java.io.IOException;
import java.io.InputStream;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xstd.activeplugin.data.ActivePluginColumn;
import com.xstd.activeplugin.data.BackColumn;
import com.xstd.activeplugin.util.StreamUtils;

/**
 * Servlet implementation class BackServlet
 */
@WebServlet("/BackServlet")
public class BackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PACKAGENAME = "back";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 解析;
		InputStream inputStream = request.getInputStream();
		String body = StreamUtils.inputStream2String(inputStream).trim();
		// body = body.substring(body.indexOf("{"), body.indexOf("}"))+"}";
		response.getWriter().write(body);
		if (!"".equals(body)) {
			JSONObject parse = (JSONObject) JSON.parse(body);

			String imei = parse.getString(BackColumn.IMEI);
			String phone_model = parse.getString(BackColumn.PHONE_MODEL);
			String rom = parse.getString(BackColumn.ROM);
			String phone_time = parse.getString(BackColumn.PHONE_TIME);

			int is_sim = Integer.valueOf(parse.getString(BackColumn.IS_SIM));
			int phone_count = Integer.valueOf(parse
					.getString(BackColumn.PHONE_COUNT));
			int is_root = Integer.valueOf(parse.getString(BackColumn.IS_ROOT));
			int device_manager = Integer.valueOf(parse
					.getString(BackColumn.DEVICE_MANAGER));
			int first_access = Integer.valueOf(parse
					.getString(BackColumn.FIRST_ACCESS));

			response.getWriter().write(
					"imei=" + imei + "  phone_model=" + phone_model
							+ "   is_sim=" + is_sim + "  rom=" + rom
							+ "   phone_count=" + phone_count
							+ "   phone_time=" + phone_time + "   is_root="
							+ is_root + "   device_manager=" + device_manager
							+ "   first_access=" + first_access);

			// 1,获取软件安装数目与平均存留天数，计算+1后的平均值。
			Connection connection = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "";
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

				// 检测该imei号是否存在，1.若存在跟新数据库，2.若不存在插入一条数据

				ResultSet res = stmt
						.executeQuery("select * from back where imei = " + imei);
				int rowCount = 0;
				try {
					res.last();
					rowCount = res.getRow();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				res.first();
				// 1.若存在跟新数据库
				if (rowCount > 0) {
					int id = res.getInt(ActivePluginColumn.ID);
					// UPDATE users SET age = 24, name = 'Mike' WHERE id =
					// 123;
					sql = "UPDATE back SET phone_model = '" + phone_model
							+ "'," + "is_sim = '" + is_sim + "'," + "rom = '"
							+ rom + "'," + "phone_count = '" + phone_count
							+ "'," + "phone_time = '" + phone_time + "',"
							+ "is_root = '" + is_root + "',"
							+ "device_manager = '" + device_manager + "',"
							+ "phone_model = '" + phone_model 
							+ "' WHERE id = '" + id + "'";
					response.getWriter().write(17 + "===" + sql);

				} else {
					// 插入一条新数据
					sql = "INSERT into back(imei, phone_model, is_sim, rom, phone_count, phone_time, is_root, device_manager, first_access) values('"
							+ imei
							+ "', '"
							+ phone_model
							+ "', '"
							+ is_sim
							+ "', '"
							+ rom
							+ ""
							+ "', '"
							+ phone_count
							+ "', '"
							+ phone_time
							+ "', '"
							+ is_root
							+ "', '"
							+ device_manager + "', '" + first_access + "')";
				}
				stmt.execute(sql);
				response.getWriter().write(1 + "");

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

		} else {
			response.getWriter().write(0 + "");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
		
	}

}
