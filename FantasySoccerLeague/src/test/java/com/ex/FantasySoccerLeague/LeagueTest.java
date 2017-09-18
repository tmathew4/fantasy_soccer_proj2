package com.ex.FantasySoccerLeague;

import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Dao.Position_Dao;
import com.ex.FantasySoccerLeague.Dao.Team_Dao;

import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class LeagueTest {
    @Mock
    private ApplicationServices viewLeagues;
    @InjectMocks
    private FrontController frontController;
    private MockMvc mockMvc;
    private League league1;
    private League league2;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(frontController).build();
        List<League> leagueList = new ArrayList<>();
        league1 = new League();
        league2 = new League();
        league1.setId(1);league2.setId(2);
        league1.setName("league1");league2.setName("league2");
        leagueList.add(league1);
        leagueList.add(league2);
//        ObjectMapper mapper = new ObjectMapper();
//        String j;
//        try {
//            j = mapper.writeValueAsString(leagueList).toString();
//        } catch(JsonProcessingException e) {
//            e.printStackTrace();
//        }
        when(viewLeagues.viewAllLeagues()).thenReturn(leagueList);
    }
    @Test
    //happy path
    public void returnsAllPlayers() throws Exception {
        mockMvc.perform(get("/league_list"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$[0].id", is(league1.getId())))
//                .andExpect(jsonPath("$[1].id", is(league2.getId())));
    }


}





