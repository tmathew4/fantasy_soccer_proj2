package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Position;
import com.ex.FantasySoccerLeague.tables.Position_Types;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Service
public class DataRetriever {

    private String url = "https://www.easports.com/fifa/ultimate-team/api/fut/item?page=";

    @Autowired
    Position_Dao pos;

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
        System.out.println(s);
        if(s.equals("GK")) {
            if(pos.getOne(2) != null)
                return pos.getOne(2);
//            p.setId(Position_Types.GOALIE.position());
            p.setId(2);
            p.setName("Goal Keeper");
        } else if(s.equals("RB") || s.equals("LB") || s.equals("CB")) {
//            p.setId(Position_Types.DEFENSE.position());
            if(pos.getOne(1) != null)
                return pos.getOne(1);
            p.setId(1);
            p.setName("Defense");
        } else if(s.equals("RW") || s.equals("LW")|| s.equals("ST")) {
//            p.setId(Position_Types.FOWARD.position());
            if(pos.getOne(3) != null)
                return pos.getOne(3);
            p.setId(3);
            p.setName("Forward");
        } else { //if(s.equals("CDM") || s.equals("CAM") || s.equals("CM") || s.equals("RM") || s.equals("LM")) {
//            p.setId(Position_Types.MIDFIELDERS.position());
            if (pos.getOne(4) != null)
                return pos.getOne(4);
            p.setId(4);
            p.setName("Midfield");
        }
        return p;
    }
}
