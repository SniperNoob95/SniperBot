package Utils;

import Bot.SniperBot;

import java.util.TimerTask;

public class HealthCheckTimerTask extends TimerTask {

    public HealthCheckTimerTask() {

    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis() / 1000;
        SniperBot.APIClient.updateHealthCheck(currentTime);
    }
}
