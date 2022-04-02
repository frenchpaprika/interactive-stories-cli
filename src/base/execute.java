package base;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

//Story tree traveller/explorer
//
//It starts with a text. The user can input a selection of things available for the user to do in the current environment.
//For each 'env' there's a list of available options.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class execute {
    private static JSONArray list;

    public static void startHere(String filename) throws IOException, ParseException {
        FileReader fr = new FileReader(filename);


//        BufferedReader br = new BufferedReader(fr);
//        String line;
//        while ((line = br.readLine()) != null) {
//            System.out.println(line);
//        }
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(fr);

        list = (JSONArray) obj;

//        System.out.println(list);

        list.forEach(jsonInput -> extraction((JSONObject) jsonInput, 1));


    }

    private static void extraction(JSONObject jsonInput, int id) {
//        System.out.println(jsonInput);
//        JSONObject jsonObject = (JSONObject) jsonInput.get(0);
        if ( (long) jsonInput.get("id") == id) {
            String outString;
            if (jsonInput.get("type").toString().equals("action")) {
                outString = "[>] ";
            } else if (jsonInput.get("type").toString().equals("question")) {
                outString = "[?] ";
            } else {
                outString = "[^] ";
            }
            System.out.println("--"  + (String) jsonInput.get("name") + "--");
            System.out.println(outString  + (String) jsonInput.get("description"));
            String tempOptions = jsonInput.get("options").toString();

            //convert the string from .get("options") to an integer array
            tempOptions = tempOptions.substring(1, tempOptions.length() - 1);
            int[] numArrayOptions = Arrays.stream(tempOptions.split(",")).mapToInt(Integer::parseInt).toArray();

            for (int elementID : numArrayOptions) {
                System.out.println(extractOption(elementID));
            }

        }

    }

    /*
     * Takes an element id as an input and returns the associated title.
     *
     * returns title of a given id if element was found
     */
    private static String extractOption(int id) {
        for (Object element : list) {
            JSONObject jsonElement = (JSONObject) element;
            if ( (long) jsonElement.get("id") == id) {
                return (String) jsonElement.get("name");
            }
        }
        return "Element with id " + id + " not found.";
    }

//    private static int[] extractArray(int id) {
//        int[] returnArray;
//        String tempString;
//        for (Object element : list) {
//            JSONObject jsonElement = (JSONObject) element;
//            if ( (int) jsonElement.get("id") == id) {
//                tempString = (String) jsonElement.get("options");
//            }
//        }
//        return new int[]{1};
//    }

    public static void main(String[] args) {
        try {
            startHere("./src/base/murder-in-edogawa.json");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("IO Exeption");
        }
    }
}
