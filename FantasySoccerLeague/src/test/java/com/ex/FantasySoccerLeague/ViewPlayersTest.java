package com.ex.FantasySoccerLeague;

import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Dao.Position_Dao;
import com.ex.FantasySoccerLeague.Dao.Team_Dao;

import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ex.FantasySoccerLeague.tables.Fantasy_User;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FantasySoccerLeagueApplication.class
)
public class ViewPlayersTest {
    @Mock
    private ApplicationServices viewPlayers;
    @InjectMocks
    private FrontController frontController;
    private Position_Dao position;

    private MockMvc mockMvc;
    private Fantasy_User testUser;
    private List<Player> players;
    private Team_Dao team;

    private Player player1;
    private Player player2;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(frontController).build();
        Team myteam = new Team();
        myteam.setId(1);
        myteam.setName("test team");

        Position positionTest = new Position();
        positionTest.setId(1);
        positionTest.setName("Forward");

        player1 = new Player();
        player2 = new Player();
        player1.setName("Thomas Mathew");player2.setName("August duet");
        player1.setPercentage(99);player2.setPercentage(2);
        player1.setAssists(50);player2.setAssists(2);
        player1.setGoals(99);player2.setGoals(1);
        player1.setId(1);player2.setId(2);
        player1.setPosition(positionTest);player2.setPosition(positionTest);
        player1.setNumber(10);player2.setNumber(7);
        player1.setOwn_Goals(100);player2.setOwn_Goals(2);
        player1.setRed_Card(1);player2.setRed_Card(25);
        player1.setSOG(100);player2.setSOG(5);
        player2.setTeam(myteam); //player1 does not have a team and therefore an available player!
        player1.setYellow_Card(2);player2.setYellow_Card(1);
        List <Player> playerList= new ArrayList<Player>();
        List <Player> playerList2= new ArrayList<Player>();
        List <Player> playerList3= new ArrayList<Player>();
        playerList.add(player1);
        playerList.add(player2);
        playerList2.add(player1);
        playerList3.add(player2);
        when(viewPlayers.findAllPlayers()).thenReturn(playerList);
        when(viewPlayers.findAvailablePlayers()).thenReturn(playerList2);
        when(viewPlayers.findUnavailablePlayers()).thenReturn(playerList3);
    }
   @Test
    //happy path
    public void returnsAllPlayers() throws Exception {
        mockMvc.perform(get("/allPlayers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(player1.getId())))
                .andExpect(jsonPath("$[1].id", is(player2.getId())));
    }

    @Test
    //happy path
    public void returnsAvailablePlayers() throws Exception {
        mockMvc.perform(get("/availablePlayers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(player1.getId())));
    }

    @Test
    //happy path
    public void returnsUnavailablePlayers() throws Exception {
        mockMvc.perform(get("/unavailablePlayers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(player2.getId())));
    }
}





