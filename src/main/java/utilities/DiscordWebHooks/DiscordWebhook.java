package utilities.DiscordWebHooks;

import com.google.gson.Gson;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import utilities.DiscordWebHooks.connection.Response;
import utilities.DiscordWebHooks.connection.WebhookException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.dreambot.api.utilities.Logger.log;

public class DiscordWebhook {
    private static final Gson gson = new Gson();
    private String webhook;

    public DiscordWebhook(String webhook) {
        this.webhook = webhook;
    }

    public void sendMessage(Payload dm) {
        new Thread(() -> {
            String strResponse = HttpRequest.post(webhook)
                    .acceptJson()
                    .contentType("application/json")
                    .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                    .send(gson.toJson(dm))
                    .body();

            if (!strResponse.isEmpty()) {
                Response response = gson.fromJson(strResponse, Response.class);
                try {
                    if (response.getMessage().equals("You are being rate limited.")) {
                        throw new WebhookException(response.getMessage());
                    }
                } catch (Exception e) {
                    throw new WebhookException(strResponse);
                }
            }
        }).start();
    }
    public void sendMessage(File... files) {
        new Thread(() -> {
            FileInputStream fis = null;
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();

                // server back-end URL
                HttpPost httppost = new HttpPost(webhook);
                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                // set the file input stream and file name as arguments
                for (int i = 0; i < files.length; i++) {
                    File inFile = files[i];
                    fis = new FileInputStream(inFile);
                    entityBuilder.addBinaryBody("file" + i, fis, ContentType.DEFAULT_BINARY, inFile.getName());
                }
                entityBuilder.addTextBody("payload_json", "{\"content\": \"<@&533839633537171487>\"}", ContentType.APPLICATION_JSON);

                //entity.addPart("payload_json", new StringBody("{\"content\": \"<@&533839633537171487>\"}", Consts.UTF_8));
                //httppost.setEntity(entity);
                httppost.setEntity(entityBuilder.build());
                // execute the request
                HttpResponse response = httpclient.execute(httppost);

                int statusCode = response.getCode();

                HttpEntity responseEntity = ((CloseableHttpResponse) response).getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");
                if (statusCode==400) throw new WebhookException(responseString);
                System.out.println("[" + statusCode + "] " + responseString);
            } catch (IOException | ParseException e) {
                System.err.println("Unable to read file");
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) fis.close();
                } catch (IOException ignored) {
                }
            }
        }).start();
    }

    public void sendMessage(Payload dm,File... files) {
        new Thread(() -> {
            FileInputStream fis = null;
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();

                // server back-end URL
                HttpPost httppost = new HttpPost(webhook);
                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                // set the file input stream and file name as arguments
                for (int i = 0; i < files.length; i++) {
                    File inFile = files[i];
                    fis = new FileInputStream(inFile);
                    entityBuilder.addBinaryBody("file" + i, fis, ContentType.DEFAULT_BINARY, inFile.getName());
                }
                entityBuilder.addTextBody("payload_json",gson.toJson(dm), ContentType.APPLICATION_JSON);
                httppost.setEntity(entityBuilder.build());
                // execute the request
                HttpResponse response = httpclient.execute(httppost);

                int statusCode = response.getCode();
                log("Discord webhook response: "+statusCode);
                HttpEntity responseEntity = ((CloseableHttpResponse) response).getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");

                //HttpEntity responseEntity = response.get
                //String responseString = EntityUtils.toString(responseEntity, "UTF-8");
                //if (statusCode==400) throw new WebhookException(responseString);
                //System.out.println("[" + statusCode + "] " + responseString);

            } catch (ClientProtocolException e) {
                System.err.println("Unable to make connection");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Unable to read file");
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) fis.close();
                } catch (IOException ignored) {
                }
            }
        }).start();
    }
    /*public void sendMessage(Payload dm,File... files) {
        new Thread(() -> {
            FileInputStream fis = null;
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();

                // server back-end URL
                HttpPost httppost = new HttpPost(webhook);
                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                // set the file input stream and file name as arguments
                for (int i = 0; i < files.length; i++) {
                    File inFile = files[i];
                    fis = new FileInputStream(inFile);
                    entityBuilder.addBinaryBody("file" + i, fis, ContentType.DEFAULT_BINARY, inFile.getName());
                }
                entityBuilder.addTextBody("payload_json", "{\"content\": \""+dm+"\"}", ContentType.APPLICATION_JSON);
                httppost.setEntity(entityBuilder.build());
                // execute the request
                HttpResponse response = httpclient.execute(httppost);

                int statusCode = response.getCode();
                HttpEntity responseEntity = ((CloseableHttpResponse) response).getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");
                if (statusCode==400) throw new WebhookException(responseString);
                System.out.println("[" + statusCode + "] " + responseString);
            }  catch (IOException | ParseException e) {
                System.err.println("Unable to read file");
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) fis.close();
                } catch (IOException ignored) {
                }
            }
        }).start();
    }*/
    public void sendMessageWithSlackFormatting(Payload dm) {
        new Thread(() -> {
            String strResponse = HttpRequest.post(webhook+"/slack")
                    .acceptJson()
                    .contentType("application/json")
                    .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                    .send(gson.toJson(dm))
                    .body();

            if (!strResponse.isEmpty()) {
                Response response = gson.fromJson(strResponse, Response.class);
                try {
                    if (response.getMessage().equals("You are being rate limited.")) {
                        throw new WebhookException(response.getMessage());
                    }
                } catch (Exception e) {
                    throw new WebhookException(strResponse);
                }
            }
        }).start();
    }
    public String sendGet() {
        return (HttpRequest.get(webhook)
                .acceptJson()
                .contentType("application/json")
                .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                .body());
    }
    public void sendDelete(){
        new Thread(() -> {
            String strResponse = HttpRequest.delete(webhook)
                    .acceptJson()
                    .contentType("application/json")
                    .header("User-Agent", "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                    .body();
            if (!strResponse.isEmpty()) {
                throw new WebhookException(strResponse);
            }
        }).start();
    }
    public static String sendJsonToWebhook(String url, String json) throws MalformedURLException {
        return HttpRequest.post(new URL(url))
                .acceptJson()
                .contentType("application/json")
                .header(HttpRequest.HEADER_USER_AGENT, "Mozilla/5.0 (X11; U; Linux i686) Gecko/20071127 Firefox/2.0.0.11") // Why? Because discordapp.com blocks the default User Agent
                .send(json)
                .body();
    }

}