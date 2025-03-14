package service;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectDB {

	// trả về chuỗi kết nối
	public static String getURL() {
		String url = null;
		try (
				// var input = new FileInputStream("db.properties");
				var input = new FileInputStream(
						"C:\\Users\\lebao\\OneDrive\\Máy tính\\Export\\db.properties");

		) {

			var p = new Properties();
			p.load(input);

			url = p.getProperty("url")
			  + p.getProperty("serverName") + ":"
			  + p.getProperty("port")
			  + "; databaseName=" + p.getProperty("databaseName")
			  + "; user=" + p.getProperty("user")
			  + "; password=" + p.getProperty("password")
			  + "; encrypt=" + p.getProperty("encrypt")
			  + "; trustServerCertificate="
			  + p.getProperty("trustServerCertificate");

		} catch (Exception e) {
			e.printStackTrace();
			url = null;
		}
		return url;
	}

	// trả về connection
	public static Connection getCon() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(getURL());
		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
		}
		return conn;
	}
}


