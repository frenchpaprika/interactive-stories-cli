package InvasionOfTheHashes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import InvasionOfTheHashes.TextIO;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class execute {
    private static Map<Long, String> type = new HashMap<Long, String>();
    private static Map<Long, String> name = new HashMap<Long, String>();
    private static Map<Long, String> description = new HashMap<Long, String>();
    private static Map<Long, int[]> options = new HashMap<>();

    public static void main(String[] args) {

        try {
            FileReader fr = new FileReader("./src/base/murder-in-edogawa.json");
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(fr);
            JSONArray list = (JSONArray) obj;
            list.forEach(jsonInput -> updateHashMap((JSONObject) jsonInput));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        startHere();

    }

    private static void updateHashMap(JSONObject jsonObject) {
        type.put((long)jsonObject.get("id"), jsonObject.get("type").toString());
        name.put((long)jsonObject.get("id"), jsonObject.get("name").toString());
        description.put((long)jsonObject.get("id"), jsonObject.get("description").toString());


        //convert the string from .get("options") to an integer array
        if (jsonObject.get("options").toString().equals("null")) {
            options.put((long)jsonObject.get("id"), new int[]{0});
        } else {
            String tempOptions = jsonObject.get("options").toString();
            tempOptions = tempOptions.substring(1, tempOptions.length() - 1);
            int[] numArrayOptions = Arrays.stream(tempOptions.split(",")).mapToInt(Integer::parseInt).toArray();

            options.put((long)jsonObject.get("id"), numArrayOptions);
        }
    }

    private static void startHere(){
        printElement(1);

    }

    private static void printElement (long id) {
        System.out.println(name.get(id));
        System.out.println(description.get(id));
        for (int option : options.get(id)) {
            System.out.println(option + " " + name.get((long)option));
        }
        printElement(TextIO.getlnLong());
    }
}
