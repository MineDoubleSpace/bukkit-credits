package me.mdspace.credits;

import me.mdspace.credits.shop.Shop;
import me.mdspace.credits.shop.ShopCommand;
import me.mdspace.credits.shop.ShopListener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Credits extends JavaPlugin {
	private String dbName;
	private String dbHost;
	private String dbUser;
	private String dbPass;
	private String dbPort;
	private static Credits instance;
	private static CreditSQL sql;
	private int startAmount;
	private String prefix;
	private boolean permissions;

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		reloadConfig();
		registerCommand();
		FileConfiguration config = this.getConfig();
		this.dbName = config.getString("database-name", "");
		this.dbHost = config.getString("database-host", "");
		this.dbUser = config.getString("database-user", "");
		this.dbPass = config.getString("database-pass", "");
		this.dbPort = config.getString("database-port", "");
		this.permissions = config.getBoolean("use-permissions", false);
		sql = createConnection();
		createTables();
		getDatabaseConfig();		
		Shop.get().setup();
		super.onEnable();
	}

	@Override
	public void onDisable() {
		sql.closeConnection();
		super.onDisable();
	}

	private CreditSQL createConnection() {
		return new CreditSQL(dbHost, dbPort, dbName, dbUser, dbPass);
	}

	private void registerCommand() {
		getCommand("credits").setExecutor(new CreditCommands());
		getCommand("shop").setExecutor(new ShopCommand());
		getServer().getPluginManager().registerEvents(new ShopListener(), this);
		getServer().getPluginManager().registerEvents(new CreditListener(), this);
	}

	private void getDatabaseConfig() {
		try {
			String prefix = sql.getValueFromRow(sql.getRowByColumn("config", "Config", "prefix"), "Value");
			if (prefix == null) prefix = "";
			this.prefix = ChatColor.translateAlternateColorCodes('&', prefix + " ");
			System.out.println(this.prefix);
		} catch (Exception e) {
			this.prefix = "";
		}
		try {
			this.startAmount = Integer.parseInt(sql.getValueFromRow(sql.getRowByColumn("config", "Config", "start Amount"), "Value"));
		} catch (Exception e) {
			startAmount = 0;
		}

	}

	private void createTables() {
		String[] values = new String[] { "ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY", "Username VARCHAR(17)", "Credits INT(11)" };
		sql.createTable("credits", values);
		values = new String[] { "`Config` VARCHAR(20) PRIMARY KEY", "`Value` VARCHAR(30)" };
		sql.createTable("config", values);
		values = new String[] { "ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY", "item INT", "name VARCHAR(60)", "enchantments VARCHAR(6)", "donator VARCHAR(60)", "cost INT", "byte INT" };
		sql.createTable("shop", values);
	}

	public String getDbName() {
		return dbName;
	}

	public String getDbHost() {
		return dbHost;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPass() {
		return dbPass;
	}

	public static Credits getPlugin() {
		//if (Instance == null) Instance = new Credits();
		return instance;
	}

	public static CreditSQL getSQL() {
		return sql;
	}

	public int getStartAmount() {
		return startAmount;
	}

	public String getPrefix() {
		return prefix;
	}

	public boolean isPermissions() {
		return permissions;
	}

}
