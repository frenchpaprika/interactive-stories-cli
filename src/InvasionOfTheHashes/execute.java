package InvasionOfTheHashes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import InvasionOfTheHashes.TextIO;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class execute {
    private static Map<Long, String> type = new HashMap<Long, String>();
    private static Map<Long, String> name = new HashMap<Long, String>();
    private static Map<Long, String> description = new HashMap<Long, String>();
    private static Map<Long, int[]> options = new HashMap<>();
    private static ArrayList<Long> progress = new ArrayList<Long>();

    public static void main(String[] args) {

        try {
            FileReader fr = new FileReader("./src/InvasionOfTheHashes/murder-in-edogawa.json");
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

    /*
     * check for input between 0 and list.length
     */
    private static void printElement (long id) {
        progress.add(id);
        System.out.println(name.get(id));
        System.out.println(description.get(id));
        for (int i = 0; i < options.get(id).length; i++) {
            //print the local option number and the title
            System.out.println(i + " " + name.get((long)options.get(id)[i]));
        }
        boolean correctInput = false;

        //check if input is a number
        while(!correctInput) {
            long tempNumber = 0;
            String tempString = TextIO.getln();
            try {
                tempNumber = Long.parseLong(tempString);
                //check if number is one of the options
                if (tempNumber >= 0 && tempNumber < options.get(id).length) {
                    printElement(options.get(id)[(int) tempNumber]);
                    correctInput = true;
                }
                System.out.println("E: Out of bounds");
            } catch (NumberFormatException e) {

                if (tempString.equals("x")) {
                    eXit();
                    correctInput = true;
                }
                System.out.println("E: Input a number");
            }
        }
    }


//    private static void printElement (long id) {
//        progress.add(id);
//        System.out.println(name.get(id));
//        System.out.println(description.get(id));
//        for (int option : options.get(id)) {
//            System.out.println(option + " " + name.get((long)option));
//        }
//        boolean correctInput = false;
//
//        //check if input is a number
//        while(!correctInput) {
//            long tempNumber = 0;
//            String tempString = TextIO.getln();
//            try {
//                tempNumber = Long.parseLong(tempString);
//                //check if number is one of the options
//                for (int option : options.get(id)) {
//                    if (tempNumber == option) {
//                        printElement(option);
//                        correctInput = true;
//                    }
//                }
//                System.out.println("E: Out of bounds");
//            } catch (NumberFormatException e) {
//
//                if (tempString.equals("x")) {
//                    eXit();
//                    correctInput = true;
//                }
//                System.out.println("E: Input a number");
//            }
//        }
//    }


    private static void eXit() {
        System.out.println("\nSuccessfully quit");
        System.out.println(progress.toString());
    }
}
