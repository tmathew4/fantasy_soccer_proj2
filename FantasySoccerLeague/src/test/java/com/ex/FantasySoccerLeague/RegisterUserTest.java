package com.ex.FantasySoccerLeague;

import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.testifyproject.guava.common.net.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FantasySoccerLeagueApplication.class
)
public class RegisterUserTest
{
    @Mock
    private ApplicationServices mApplicationServices;

    @InjectMocks
    private FrontController mFrontController;

    private MockMvc mMockMvc;
    String mJson;

    private Fantasy_User mFantasyUser;

    @Before
    public void setup() throws JsonProcessingException
    {
            MockitoAnnotations.initMocks(this);
            mMockMvc = MockMvcBuilders.standaloneSetup(mFrontController).build();

            mFantasyUser = new Fantasy_User();
            mFantasyUser.setId(1);
            mFantasyUser.setEmail("Vincent.Bolden.12@cnu.edu");
            mFantasyUser.setPassword("1044922");
            mFantasyUser.setFName("Vincent");
            mFantasyUser.setLName("Bolden");

            ObjectMapper objectMapper = new ObjectMapper();
            mJson = objectMapper.writeValueAsString(mFantasyUser);
    }

    @Test
    public void returnsSuccessfulRegisterUser() throws Exception
    {
        mMockMvc.perform(post("/register_user")
                .content(mJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("Vincent.Bolden.12@cnu.edu")));
    }
}
