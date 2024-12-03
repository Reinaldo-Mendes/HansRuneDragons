package config;

public class WebhookSettings {
    private static WebhookSettings webhookSettings = new WebhookSettings();

    private WebhookSettings getWebhookSettings() {
        return webhookSettings;
    }

    public static void setWebhookSettings(WebhookSettings webhookSettings) {
        WebhookSettings.webhookSettings = webhookSettings;
    }

    private WebhookSettings() {

    }

    private String URL;
    private int minutesBetweenMessages;
    private boolean sendOnRareDrop;
    private boolean sendOnLevelUp;


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getMinutesBetweenMessages() {
        return minutesBetweenMessages;
    }

    public void setMinutesBetweenMessages(int minutesBetweenMessages) {
        this.minutesBetweenMessages = minutesBetweenMessages;
    }

    public boolean isSendOnRareDrop() {
        return sendOnRareDrop;
    }

    public void setSendOnRareDrop(boolean sendOnRareDrop) {
        this.sendOnRareDrop = sendOnRareDrop;
    }

    public boolean isSendOnLevelUp() {
        return sendOnLevelUp;
    }

    public void setSendOnLevelUp(boolean sendOnLevelUp) {
        this.sendOnLevelUp = sendOnLevelUp;
    }
}
