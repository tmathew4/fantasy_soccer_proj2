package com.ex.FantasySoccerLeague;
import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
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

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(frontController).build();

        testTeam = new Team();
        testTeam.setName("WallyWorldMagots");
        testTeam.setId(12);


        when(myTeam.myTeam(12)).thenReturn((testTeam));
    }

    @Test
    public void returnAMyTeam() throws Exception {
                mockMvc.perform(get("/Home/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testTeam.getId())));
    }

}