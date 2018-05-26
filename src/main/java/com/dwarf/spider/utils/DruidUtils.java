package com.dwarf.spider.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DruidUtils {
	private static Logger logger = Logger.getLogger(DruidUtils.class);
	private static Properties prop = new Properties();
	{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("server.properties");
		try {
			prop.load(in);
			logger.info("server.properties loading succeed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DruidUtils utils;
	private DruidDataSource dataSource;
	private PreparedStatement pstmt;
	private Connection conn;
	private ResultSet resultSet;
	
	public DruidUtils(){
		try{
			dataSource = new DruidDataSource();       
	        dataSource.setUsername(prop.getProperty("jdbc_username"));       
	        dataSource.setPassword(prop.getProperty("jdbc_password"));       
	        dataSource.setUrl(prop.getProperty("jdbc_url"));
	        dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 
	        dataSource.setInitialSize(10); 
	        dataSource.setMinIdle(10); 
	        dataSource.setMaxActive(20); 
	        dataSource.setPoolPreparedStatements(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static synchronized DruidUtils getInstance(){
		if(utils == null){
			utils = new DruidUtils();
		}
		return utils;
	}
	
	public Connection getConnection(){
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public synchronized ResultSet getResult(String sql){
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pstmt = conn.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultSet;
	}
	
	
	
	public synchronized void close(){
		try {
			this.resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		DruidUtils.getInstance().getConnection();
	}

}
