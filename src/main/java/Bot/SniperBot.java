package Bot;

import EventManager.EventManager;
import Utils.BotLogger;
import Utils.APIClient;
import Utils.HealthCheckTimerTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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

        jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.ALL).addEventListeners(new EventManager()).build().awaitReady();
        // jda = new JDABuilder(token).addEventListeners(new EventManager()).build();

        jda.getPresence().setActivity(Activity.watching("for commands."));

        botLogger = new BotLogger();
        APIClient = new APIClient();

        scheduleHealthCheckTask();

        botLogger.logMessage("[SniperBot.main] - New bot session started.");
    }

    private static void scheduleHealthCheckTask() {
        TimerTask healthCheckTimerTask = new HealthCheckTimerTask();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(healthCheckTimerTask, 0, 60000);
    }
}
