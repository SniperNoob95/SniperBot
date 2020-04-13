package Utils;

import Bot.SniperBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class APIClient {

    private DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private String URL;
    private HttpClient httpClient = HttpClient.newHttpClient();

    public APIClient() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        try {
            URL = resourceBundle.getString("url");
            HttpResponse<String> healthCheck = healthCheck();

            if (healthCheck.statusCode() != 200) {
                SniperBot.botLogger.logError("[APIClient] - Cannot contact API.");
                System.exit(0);
            }
        } catch (Exception e) {
            SniperBot.botLogger.logError("[APIClient] - Cannot contact API.");
            System.exit(0);
        }
    }

    private HttpResponse<String> healthCheck() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL + "/health")).build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void insertMessage(MessageReceivedEvent event) {
        JSONObject payload = new JSONObject();
        payload.put("date", new Timestamp(new Date().getTime()));
        payload.put("userName", event.getAuthor().getName());
        payload.put("userID", event.getAuthor().getId());
        payload.put("userDiscriminator", event.getAuthor().getDiscriminator());

        byte[] utf8Content = event.getMessage().getContentRaw().getBytes(StandardCharsets.UTF_8);
        payload.put("content", new String(utf8Content, StandardCharsets.UTF_8));

        try {
            HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(payload.toString())).header("Content-type", "application/json").uri(URI.create(URL + "/messages")).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                SniperBot.botLogger.logError(String.format("[APIClient.insertMessage] - Failed to insert message, got %s from server.", response.statusCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[APIClient.insertMessage] - Failed to insert message.");
        }
    }

    public void insertCommand(MessageReceivedEvent event) {
        JSONObject payload = new JSONObject();
        payload.put("date", new Timestamp(new Date().getTime()));
        payload.put("userName", event.getAuthor().getName());
        payload.put("userID", event.getAuthor().getId());
        payload.put("userDiscriminator", event.getAuthor().getDiscriminator());
        payload.put("command", event.getMessage().getContentRaw().split("\\s+")[0]);

        try {
            HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(payload.toString())).header("Content-type", "application/json").uri(URI.create(URL + "/commands")).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                SniperBot.botLogger.logError(String.format("[APIClient.insertCommand] - Failed to insert command, got %s from server.", response.statusCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[APIClient.insertCommand] - Failed to insert command.");
        }
    }

    public void insertSale(String[] saleAttributes) {
        JSONObject payload = new JSONObject();
        payload.put("date", new Timestamp(new Date().getTime()));
        payload.put("enteredID", saleAttributes[0]);
        payload.put("enteredName", saleAttributes[1]);
        payload.put("sellerID", saleAttributes[2]);
        payload.put("sellerName", saleAttributes[3]);
        payload.put("item", saleAttributes[4]);
        payload.put("itemQuality", saleAttributes[5]);
        payload.put("spell1", saleAttributes[6]);
        payload.put("spell2", saleAttributes[7]);
        payload.put("price", Double.parseDouble(saleAttributes[8]));

        try {
            HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(payload.toString())).header("Content-type", "application/json").uri(URI.create(URL + "/sales")).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                SniperBot.botLogger.logError(String.format("[APIClient.insertSale] - Failed to insert sale, got %s from server.", response.statusCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[APIClient.insertSale] - Failed to insert sale.");
        }
    }

    public int getMemberMessages(MessageReceivedEvent event) {
        try {
            URIBuilder uriBuilder = new URIBuilder(URL + "/messages/count");
            uriBuilder.addParameter("userID", event.getAuthor().getId());
            HttpRequest request = HttpRequest.newBuilder().uri(uriBuilder.build()).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseBody = new JSONObject(response.body());
            if (response.statusCode() != 200) {
                SniperBot.botLogger.logError(String.format("[APIClient.getMemberMessages] - Failed to get member messages, got %s from server.", response.statusCode()));
                return -1;
            }
            return responseBody.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[APIClient.getMemberMessages] - Failed to get member messages.");
        }
        return -1;
    }

    public int getTotalMessages() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL + "/messages/total")).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseBody = new JSONObject(response.body());
            if (response.statusCode() != 200) {
                SniperBot.botLogger.logError(String.format("[APIClient.getTotalMessages] - Failed to get total messages, got %s from server.", response.statusCode()));
                return -1;
            }
            return responseBody.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[APIClient.getTotalMessages] - Failed to get total messages.");
        }
        return -1;
    }

    public int getTotalSales() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL + "/sales/total")).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseBody = new JSONObject(response.body());
            if (response.statusCode() != 200) {
                SniperBot.botLogger.logError(String.format("[APIClient.getTotalSales] - Failed to get total sales, got %s from server.", response.statusCode()));
                return -1;
            }
            return responseBody.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[APIClient.getTotalSales] - Failed to get total sales.");
        }
        return -1;
    }

    public String getItemSales(String item) {
        try {
            URIBuilder uriBuilder = new URIBuilder(URL + "/sales/item");
            uriBuilder.addParameter("itemName", item);
            HttpRequest request = HttpRequest.newBuilder().uri(uriBuilder.build()).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray responseBody = new JSONArray(response.body());
            if (response.statusCode() != 200) {
                SniperBot.botLogger.logError(String.format("[APIClient.getItemSales] - Failed to get item sales, got %s from server.", response.statusCode()));
            }

            StringBuilder result = new StringBuilder("```Date\tItem\tQuality\tSpell1\tSpell2\tPrice (Keys)```");
            result.append("```");

            if (responseBody.length() > 18) {
                for (int i = 0; i < 17; i++) {
                    String date = dateFormat.format(parseFormat.parse(responseBody.getJSONObject(i).getString("date")));
                    String itemName = responseBody.getJSONObject(i).getString("item");
                    String quality = responseBody.getJSONObject(i).getString("itemQuality");
                    String spell1 = responseBody.getJSONObject(i).getString("spell1");
                    String spell2 = responseBody.getJSONObject(i).getString("spell2");
                    Double price = responseBody.getJSONObject(i).getDouble("price");
                    result.append(String.format("\n%s\t%s\t%s\t%s\t%s\t%s", date, itemName, quality, spell1, spell2, price));
                }
                result.append(String.format("```\n```+%s results...", responseBody.length() - 17));
                result.append("```");
                return result.toString();
            } else if (responseBody.length() != 0) {
                for (int i = 0; i < responseBody.length(); i++) {
                    String date = dateFormat.format(parseFormat.parse(responseBody.getJSONObject(i).getString("date")));
                    String itemName = responseBody.getJSONObject(i).getString("item");
                    String quality = responseBody.getJSONObject(i).getString("itemQuality");
                    String spell1 = responseBody.getJSONObject(i).getString("spell1");
                    String spell2 = responseBody.getJSONObject(i).getString("spell2");
                    Double price = responseBody.getJSONObject(i).getDouble("price");
                    result.append(String.format("\n%s\t%s\t%s\t%s\t%s\t%s", date, itemName, quality, spell1, spell2, price));
                }
                result.append("```");
                return result.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[APIClient.getItemSales] - Failed to get item sales.");
            return "Error while retrieving sales.";
        }
    }
}
