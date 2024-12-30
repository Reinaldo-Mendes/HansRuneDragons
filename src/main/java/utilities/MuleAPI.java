package utilities;

import behaviour.mule.GiveCoins;
import behaviour.mule.MuleBranch;
import config.GlobalVariables;
import mule.MulingInformation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.dreambot.api.utilities.Logger.log;

public class MuleAPI {
    private static Socket socket;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    public static boolean connectToMuleServer(int port) {
        try {
            if (socket == null || socket.isClosed()) {
                socket = new Socket("127.0.0.1", port);
                log("Socket created.");
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                log("ObjectOutputStream created on client.");
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                log("ObjectInputStream created on client.");
                log("Connected to our mule!");
                return true;
            } else {
                log("There is a connection active already!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            log("Failed to connect to our mule");
        }
        return false;
    }

    public static boolean sendMulingInformation(MulingInformation mulingInformation) {
        if (mulingInformation != null && isConnected()) {
            try {
                objectOutputStream.writeObject(mulingInformation);
                objectOutputStream.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                log("We had issues sending the muling information object to mule!");
            }
        }
        return false;
    }

    public static MulingInformation readMulingInformation() {
        if (isConnected()) {
            try {
                MulingInformation muleInformation = (MulingInformation) objectInputStream.readObject();
                log("Object received from bot: " + muleInformation.getRsn());
                return muleInformation;
            } catch (Exception e) {
                e.printStackTrace();
                log("Failed to receive object from bot.");
            }
        } else {
            log("We are not connected");
        }
        return null;
    }

    public static boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public static boolean disconnect() {
        if (isConnected()) {
            try {
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
                log("We have closed the connection!");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                log("Failed to close the connection");
            }
        } else {
            log("There is no connection to close!");
        }
        return false;
    }

    public static void resetMulingVariables (){
       MuleBranch.muleInformation = null;
        MuleBranch.hasSentNotificationToMule = false;
        GlobalVariables.muleName = "null";
    }
}
