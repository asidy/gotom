package cn.gotom.sso.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class JDBCManager
{
	protected final static Logger log = Logger.getLogger(JDBCManager.class);
	public static final JDBCManager single = new JDBCManager();
	public static final ThreadLocal<Connection> connectionPool = new ThreadLocal<Connection>();
	public static final String jdbc_driver = "jdbc.driver";
	public static final String jdbc_url = "jdbc.url";
	public static final String jdbc_username = "jdbc.username";
	public static final String jdbc_password = "jdbc.password";
	private String driver;
	private String url;
	private String username;
	private String password;

	public JDBCManager()
	{
		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("jdbc", Locale.ROOT);
			this.driver = bundle.getString(jdbc_driver);
			this.url = bundle.getString(jdbc_url);
			this.username = bundle.getString(jdbc_username);
			this.password = bundle.getString(jdbc_password);
		}
		catch (Exception ex)
		{
			log.warn(ex.getMessage());
		}
	}

	/**
	 * 创建当前线程连接
	 * 
	 * @return
	 */
	public Connection connection() throws SQLException
	{
		Connection conn = connectionPool.get();
		if (null == conn || conn.isClosed())
		{
			conn = create();
			connectionPool.set(conn);
			log.debug("Open: " + conn);
		}
		return conn;
	}

	private synchronized Connection create() throws SQLException
	{
		try
		{
			Class.forName(getDriver());
		}
		catch (Exception e)
		{
			throw new SQLException(e.getMessage(), e.getCause());
		}
		return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
	}

	/**
	 * 关闭当前线程连接
	 */
	public void close()
	{
		Connection conn = connectionPool.get();
		if (null != conn)
		{
			connectionPool.set(null);
			try
			{
				log.debug("Close:" + conn);
				conn.close();
			}
			catch (SQLException ex)
			{
				log.error(ex.getMessage(), ex);
			}
		}
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
