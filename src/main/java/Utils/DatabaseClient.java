package Utils;

import Bot.SniperBot;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DatabaseClient {

    private String dbUser;
    private String dbPassword;
    private String dbHost;
    private String dbDatabase;
    private DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    public DatabaseClient() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        try {
            dbUser = resourceBundle.getString("user");
            dbPassword = resourceBundle.getString("password");
            dbHost = resourceBundle.getString("host");
            dbDatabase = resourceBundle.getString("database");
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[DatabaseWriter] - Cannot get database config values.");
            System.exit(0);
        }
    }

    public void insertMessage(MessageReceivedEvent event) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbDatabase);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Messages (date, userName, userID, userDiscriminator, content) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setTimestamp(1, new Timestamp(new Date().getTime()));
            preparedStatement.setString(2, event.getAuthor().getName());
            preparedStatement.setString(3, event.getAuthor().getId());
            preparedStatement.setString(4, event.getAuthor().getDiscriminator());
            preparedStatement.setString(5, event.getMessage().getContentRaw());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[DatabaseWriter.insertMessage] - Failed to insert message.");
        } finally {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    public void insertCommand(MessageReceivedEvent event) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbDatabase);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Commands (date, userName, userID, userDiscriminator, command) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setTimestamp(1, new Timestamp(new Date().getTime()));
            preparedStatement.setString(2, event.getAuthor().getName());
            preparedStatement.setString(3, event.getAuthor().getId());
            preparedStatement.setString(4, event.getAuthor().getDiscriminator());
            preparedStatement.setString(5, event.getMessage().getContentRaw().split("\\s+")[0]);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[DatabaseWriter.insertCommand] - Failed to insert command.");
        } finally {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    public int memberMessages(MessageReceivedEvent event) throws SQLException{
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbDatabase);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM Messages WHERE userID = ?");
            preparedStatement.setString(1, event.getAuthor().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[DatabaseWriter.memberMessages] - Failed to get member message count.");
        } finally {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return -1;
    }

    public int totalMessages() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbDatabase);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM Messages");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[DatabaseWriter.totalMessages] - Failed to get total message count.");
        } finally {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return -1;
    }

    public int totalSales() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbDatabase);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM Sales");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[DatabaseWriter.totalSales] - Failed to get total sales count.");
        } finally {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        return -1;
    }

    public String itemSales(String item) throws SQLException{
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setServerName(dbHost);
        dataSource.setDatabaseName(dbDatabase);

        StringBuilder result = new StringBuilder("```Date\tItem\tQuality\tSpell1\tSpell2\tPrice (Keys)```");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT date, item, itemQuality, spell1, spell2, price FROM Sales WHERE item LIKE ? ORDER BY price");
            preparedStatement.setString(1, "%" + item + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            int rowCount = 0;
            while (resultSet.next()) {
                ++rowCount;
            }
            System.out.println(rowCount);
            resultSet.beforeFirst();
            result.append("\n```");
            if (rowCount > 18) {
                for (int i = 0; i < 17; i++) {
                    resultSet.next();
                    String date = dateFormat.format(resultSet.getDate("date"));
                    String itemName = resultSet.getString("item");
                    String quality = resultSet.getString("itemQuality");
                    String spell1 = resultSet.getString("spell1");
                    String spell2 = resultSet.getString("spell2");
                    String price = resultSet.getBigDecimal("price").toPlainString();
                    result.append(String.format("\\n%s\\t%s\\t%s\\t%s\\t%s\\t%s", date, itemName, quality, spell1, spell2, price));
                    result.append(String.format("+%s results...", resultSet.getFetchSize() - 17));
                }
                result.append("```");
                return result.toString();
            } else if (rowCount != 0) {
                while (resultSet.next()) {
                    String date = dateFormat.format(resultSet.getDate("date"));
                    String itemName = resultSet.getString("item");
                    String quality = resultSet.getString("itemQuality");
                    String spell1 = resultSet.getString("spell1");
                    String spell2 = resultSet.getString("spell2");
                    String price = resultSet.getBigDecimal("price").toPlainString();
                    result.append(String.format("\\n%s\\t%s\\t%s\\t%s\\t%s\\t%s", date, itemName, quality, spell1, spell2, price));
                }
                result.append("```");
                return result.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[DatabaseWriter.memberMessages] - Failed to get item sales.");
            return "Error while retrieving sales.";
        } finally {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
}
