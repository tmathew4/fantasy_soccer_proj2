package com.ex.FantasySoccerLeague;

import com.ex.FantasySoccerLeague.Controller.UserController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Trade;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FantasySoccerLeagueApplication.class
)
public class TradeTest {

    @Mock
    private ApplicationServices login;

    @InjectMocks
    private UserController userController;


    private MockMvc mockMvc;

    private Trade testTrade;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        Player p1 = new Player();
        p1.setId(1);
        Player p2 = new Player();
        p2.setId(24);

        testTrade = new Trade();
        testTrade.setPlayer2Id(p1);
        testTrade.setPlayer2Id(p2);

        when(login.tradePlayers(1, 24, 100000)).thenReturn(testTrade);
    }

    @Test
    //happy path
    public void returnsAGoodTrade() throws Exception {
        mockMvc.perform(get("/trade_player/1/24"))
                .andExpect(status().isOk());
    }
}

