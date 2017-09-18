package com.ex.FantasySoccerLeague;
import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Team;
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
public class myTeamTest {

    @Mock
    private ApplicationServices myTeam;

    @InjectMocks
    private FrontController frontController;

    private MockMvc mockMvc;

    private Team testTeam;
    private Player testPlayer;
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(frontController).build();

        testPlayer = new Player();
        testPlayer1 = new Player();
        testPlayer2 = new Player();
        testPlayer3 = new Player();
        testTeam = new Team();
        testTeam.setName("WallyWorldMagots");
        testPlayer.setName("vincent Bolden");
        testPlayer1.setName("Josh");
        testPlayer2.setName("Thomas");
        testPlayer3.setName("Thomas");
        testPlayer.setId(12);
        testTeam.setId(12);

        List<Player> players = new ArrayList<>();
        players.add(testPlayer);
        players.add(testPlayer1);
        players.add(testPlayer2);
        players.add(testPlayer3);

        when(myTeam.myTeam(12)).thenReturn(players);
    }

    @Test
    public void returnAMyTeam() throws Exception {
                mockMvc.perform(get("/team/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(testPlayer.getName())));
    }

}
