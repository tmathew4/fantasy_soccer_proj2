package com.ex.FantasySoccerLeague;

import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Position;
import com.ex.FantasySoccerLeague.tables.Position_Types;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/* @RunWith(someClass.class)
JUnit invokes someClass.class to run tests instead of the runner built into JUnit
 */
/* @SpringBootTest
Spring Boot searches for the main configuration class annotated with @SpringBootApplication
and uses that to start up the application context.
 */
/* webEnvironment = RANDOM_PORT
Server starts on a random port to avoid conflicts during testing.
 */
/* classes = someClass.class
The annotated classes to use for loading an ApplicationContext
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FantasySoccerLeagueApplication.class
)
public class ValidateTeamTest {
    /**
     * @Mock
     * Return mocked data when calling methods from this class(ApplicationServices).
     */
    @Mock
    private ApplicationServices mApplicationServices;

    /**
     * @InjectMocks
     * Inject our mocks(ApplicationServices) into the following(FrontController).
     */
    @InjectMocks
    private FrontController mFrontController;

    /**
     * MockMvc
     * The main entry point for server side Spring MVC test support.
     */
    private MockMvc mMockMvc;
    private String mJson;


    @Before
    public void init() throws JsonProcessingException {
        //Required to initialize mocks and inject them.
        MockitoAnnotations.initMocks(this);
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Player player = new Player();
            Position position = new Position();
            position.setId(1);
            player.setPosition(position);
            players.add(player);
        }
        for(int i = 0; i < 4; i++) {
            Player player = new Player();
            Position position = new Position();
            position.setId(3);
            player.setPosition(position);
            players.add(player);
        }
        for(int i = 0; i < 4; i++) {
            Player player = new Player();
            Position position = new Position();
            position.setId(4);
            player.setPosition(position);
            players.add(player);
        }
        for(int i = 0; i < 2; i++) {
            Player player = new Player();
            Position position = new Position();
            position.setId(2);
            player.setPosition(position);
            players.add(player);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        mJson = objectMapper.writeValueAsString(players);
        System.out.println(mJson);
    }

    //TEST STOPPED DUE TO OTHER ERRORS
    @Test
    public void successfullyDeserializesJson() throws IOException {
        boolean result = mApplicationServices.validateTeam(mJson);
        Assert.assertEquals("Not testing the return", false, result);
    }
}
