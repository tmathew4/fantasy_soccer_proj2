package com.ex.FantasySoccerLeague;


import com.ex.FantasySoccerLeague.Controller.UserController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Player;
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
public class DropPlayerTest {

    @Mock
    private ApplicationServices login;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private Player testPlayer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        testPlayer = new Player();
        testPlayer.setId(12);

    }

    @Test
    public void returnsADeletedPlayer() throws Exception {
        mockMvc.perform(get("/delete_player/12/24"));
    }
}
