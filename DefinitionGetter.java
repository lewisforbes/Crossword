import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.net.ssl.HttpsURLConnection;


public class DefinitionGetter {
    public static String getDefinition(String word) {
        String[] rawData = getData(getUrl(word)).split("\n");
        ArrayList<String>  definitions = new ArrayList<>();
        boolean nextLineDef = false;

        for (String line : rawData) {
            if (nextLineDef) {
                definitions.add(line);
                nextLineDef = false;
            }
            if (line.contains("\"definitions\": [")) {
                nextLineDef = true;;
            }

        }
        Collections.shuffle(definitions);
        return definitions.get(0).replace("\"", "").strip().replace(".", "");
    }

    private static String getUrl(String word) {
        final String language = "en-gb";
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }

    private static String getData(String givenUrl) {
        final String app_id = "2175fb5f";
        final String app_key = "672be18db6ba25369bbff0c7c7eb6f84";
        try {
            URL url = new URL(givenUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", app_id);
            urlConnection.setRequestProperty("app_key", app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}