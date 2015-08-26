package me.mdspace.credits;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

public class CreditSQL {

	private Connection connection = null;

	public CreditSQL(String host, String port, String database, String username, String password) {
		long start = 0;
		long end = 0;
		try {
			start = System.currentTimeMillis();
			System.out.println("Attempting to establish a connection the MySQL server!");
			Class.forName("com.mysql.jdbc.Driver");
			String statement = "CREATE DATABASE IF NOT EXISTS " + database;
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, username, password);
			Statement st = conn.createStatement();
			st.executeUpdate(statement);
			st.close();
			conn.close();
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
			end = System.currentTimeMillis();
			System.out.println("Connection to MySQL server established! (" + host + ":" + port + ")");
			System.out.println("Connection took " + ((end - start) / 1000) + "ms!");
		} catch (SQLException e) {
			System.out.println("Could not connect to MySQL server! because: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not found!");
		}
	}

	public boolean isConnected() {
		if (connection != null) {
			return true;
		}
		return false;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Couldn't close connection");
		}
	}

	public boolean execute(String sql) {
		boolean st = false;
		try {
			Statement statement = connection.createStatement();
			st = statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	public ResultSet executeQuery(String sql) {
		ResultSet st = null;
		try {
			Statement statement = connection.createStatement();
			st = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	public int executeUpdate(String sql) {
		int st = 0;
		try {
			Statement statement = connection.createStatement();
			st = statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	public void createTable(String tablename, String[] values) {
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tablename + " (");
		for (int i = 0; i < values.length; i++) {
			if (i == (values.length - 1)) {
				sql.append(values[i]);
			} else {
				sql.append(values[i] + ", ");
			}
		}
		sql.append(")");

		try {
			System.out.println("Query: " + sql.toString());
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteTable(String tablename) {
		String sql = "DROP TABLE IF EXISTS " + tablename;

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetTable(String tablename, String[] values) {
		deleteTable(tablename);
		createTable(tablename, values);
	}

	public void insertInto(String table, String[] columns, String[] values) {
		String c = "(";

		for (int i = 0; i < columns.length; i++) {
			if (i == columns.length - 1) {
				c = c + columns[i];
			} else {
				c = c + columns[i] + ",";
			}
		}

		c = c + ")";

		String v = "(";
		for (int i = 0; i < values.length; i++) {
			if (i == values.length - 1) {
				v = v + values[i];
			} else {
				v = v + values[i] + ",";
			}
		}
		v += ")";
		this.executeUpdate("INSERT INTO " + table + c + " VALUES" + v);
	}

	public ResultSet getRowByColumn(String table, String column, String value) {
		return this.executeQuery("SELECT * FROM " + table + " WHERE " + column + "=" + "'" + value + "';");
	}

	public String getValueFromRow(ResultSet set, String column) {
		try {
			if (!set.next()) {
				System.out.println("Error!! Getting Values from config!");
			}
			return set.getString(column);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getRowCount(String table) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + table);
		int count = 0;
		try {
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Connection getConnection() {
		return connection;
	}

	public boolean containsPlayer(Player player) {
		return containsPlayer(player.getName());
	}

	public boolean containsPlayer(String player) {
		ResultSet results = getRowByColumn("Credits", "Username", player);
		try {
			return results.next();
		} catch (SQLException e) {
			return false;
		}
	}

}