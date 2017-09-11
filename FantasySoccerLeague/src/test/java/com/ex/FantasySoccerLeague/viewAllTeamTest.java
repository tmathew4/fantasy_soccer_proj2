package com.ex.FantasySoccerLeague;
import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.League;
import com.ex.FantasySoccerLeague.tables.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FantasySoccerLeagueApplication.class
)
public class viewAllTeamTest {

    @Mock
    private ApplicationServices allTeams;

    @InjectMocks
    private FrontController frontController;

    private MockMvc mockMvc;

    private League testLeague;
    private Team testTeam;
    private Team testTeam2;
    private Team testTeam3;
    private Team testTeam4;
    private Team testTeam5;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(frontController).build();

        testTeam = new Team();
        testTeam2 = new Team();
        testTeam3 = new Team();
        testTeam4 = new Team();
        testTeam5 = new Team();
        testLeague = new League();

        testTeam.setName("OneTeamName");
        testTeam2.setName("TwoTeamName");
        testTeam3.setName("ThreeTeamName");
        testTeam4.setName("SomeMoreTeamNames");
        testTeam5.setName("TheLastTeamName");
        testLeague.setId(12);

        List<Team> team = new ArrayList<>();
        team.add(testTeam);
        team.add(testTeam2);
        team.add(testTeam3);
        team.add(testTeam4);
        team.add(testTeam5);
        when(allTeams.viewAllTeam(12)).thenReturn(team);
    }

    @Test
    public void returnAllTeam() throws Exception {
        mockMvc.perform(get("/Team/12"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is(testTeam.getName())));
    }
}
