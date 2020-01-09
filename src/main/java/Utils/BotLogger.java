package Utils;

import Bot.SniperBot;

import java.util.Objects;

public class BotLogger {

    private long botLogChannel;

    public BotLogger() {
        try {
            this.botLogChannel = SniperBot.jda.getTextChannelsByName("bot-log", false).get(0).getIdLong();
        } catch (Exception e) {
            e.printStackTrace();
            botLogChannel = 0L;
            System.out.println("Couldn't find logging channel.");
        }
    }

    public void logError(String errorMessage) {
        try {
            Objects.requireNonNull(SniperBot.jda.getTextChannelById(botLogChannel)).sendMessage(String.format("ERROR: %s", errorMessage)).queue();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't find logging channel.");
        }
    }

    public void logMessage(String message) {
        try {
            Objects.requireNonNull(SniperBot.jda.getTextChannelById(botLogChannel)).sendMessage(String.format("LOG: %s", message)).queue();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't find logging channel.");
        }
    }
}
