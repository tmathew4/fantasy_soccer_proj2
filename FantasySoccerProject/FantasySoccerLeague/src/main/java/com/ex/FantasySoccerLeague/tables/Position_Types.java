package com.ex.FantasySoccerLeague.tables;

public enum Position_Types {

    DEFENSE (1), GOALIE (2), FOWARD (3), MIDFIELDERS (4);
    private int id;

    Position_Types(int Position){
        id = Position;
    }
}
