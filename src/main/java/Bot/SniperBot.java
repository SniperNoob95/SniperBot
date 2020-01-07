package Bot;

import EventManager.EventManager;
import Utils.BotLogger;
import Utils.DatabaseClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.ResourceBundle;

public class SniperBot {
    public static JDA jda;
    public static String prefix = "!";
    public static DatabaseClient databaseClient;
    public static BotLogger botLogger;

    public static void main(String[] args) throws LoginException, InterruptedException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        String token = null;
        try {
            token = resourceBundle.getString("token");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot get Discord token.");
            System.exit(0);
        }

        jda = new JDABuilder(token).addEventListeners(new EventManager()).build();
        jda.awaitReady();
        jda.getPresence().setActivity(Activity.listening("for commands."));

        botLogger = new BotLogger();
        databaseClient = new DatabaseClient();

        botLogger.logMessage("[FireBot.main] - New bot session started.");
    }
}
