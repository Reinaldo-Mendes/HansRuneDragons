package config;

public class WebhookSettings {
    public WebhookSettings() {

    }

    private String URL = "https://discord.com/api/webhooks/1234633748188758129/acnc3EX5k0hdmh-S8rraVRf3Cw1hITR-nDSti0JCEIQ6Rt59dndu3nyxi-ftk4dW9QXL";
    private int minutesBetweenMessages = 5;
    private boolean sendOnRareDrop = false;
    private boolean sendOnLevelUp = false;
    private boolean sendOnMuleEvent = true;


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

    public boolean isSendOnMuleEvent() {
        return sendOnMuleEvent;
    }

    public void setSendOnMuleEvent(boolean sendOnMuleEvent) {
        this.sendOnMuleEvent = sendOnMuleEvent;
    }

}
