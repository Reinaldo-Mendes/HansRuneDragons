package mule;

import java.io.Serializable;

public class MulingInformation implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String rsn;
    private double value;
    private int world;
    private String message;
    private String requestType;
    private int[] positionCoordinates;

    public String getRsn() {
        return rsn;
    }

    public void setRsn(String rsn) {
        this.rsn = rsn;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getWorld() {
        return world;
    }

    public void setWorld(int world) {
        this.world = world;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int[] getPositionCoordinates() {
        return positionCoordinates;
    }

    public void setPositionCoordinates(int[] positionCoordinates) {
        this.positionCoordinates = positionCoordinates;
    }
}
