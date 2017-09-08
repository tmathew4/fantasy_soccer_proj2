package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Position;
import com.ex.FantasySoccerLeague.tables.Position_Types;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class DataRetriever {

    private String url = "https://www.easports.com/fifa/ultimate-team/api/fut/item?page=";

    public static void main(String[] args) {
        System.out.println(new DataRetriever().get(1).toString());//get("items").get(0).get("firstName").textValue());//.textValue());
    }

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

    public Position getPosition(String s) {
        Position p = new Position();
        if(s.equals("GK")) {
            p.setId(Position_Types.GOALIE.position());
            p.setName("Goalie");
        } else if(s.equals("RW") || s.equals("LW")) {
            p.setId(Position_Types.MIDFIELDERS.position());
            p.setName("Midfield");
        } else if(s.equals("RB") || s.equals("LB")) {
            p.setId(Position_Types.DEFENSE.position());
            p.setName("Defense");
        } else if(s.equals("ST")) {
            p.setId(Position_Types.FOWARD.position());
            p.setName("Forward");
        }
        return null;
    }
}
