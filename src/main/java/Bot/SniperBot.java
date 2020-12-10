package Bot;

import EventManager.EventManager;
import Utils.BotLogger;
import Utils.APIClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.ResourceBundle;

public class SniperBot {
    public static JDA jda;
    public static String prefix = "!";
    public static APIClient APIClient;
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

        jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).addEventListeners(new EventManager()).build().awaitReady();
        // jda = new JDABuilder(token).addEventListeners(new EventManager()).build();

        jda.getPresence().setActivity(Activity.watching("for commands."));

        botLogger = new BotLogger();
        APIClient = new APIClient();

        botLogger.logMessage("[SniperBot.main] - New bot session started.");
    }
}
