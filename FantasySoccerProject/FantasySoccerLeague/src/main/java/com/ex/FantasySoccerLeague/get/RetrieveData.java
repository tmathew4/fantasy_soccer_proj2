package com.ex.FantasySoccerLeague.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RetrieveData {

    private String url = "https://www.easports.com/fifa/ultimate-team/api/fut/item?page=";

//    public static void main(String[] args) {
//        System.out.println(new RetrieveData().get(1).size());//get("items").get(0).get("firstName").textValue());//.textValue());
//    }

    private String getJson(int i) {
        try {
            URL u = new URL(url + i);
            InputStream is = u.openStream();
            Scanner s = new Scanner(is);
            return s.nextLine();
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonNode get(int i) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(getJson(i)).get("items");
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
