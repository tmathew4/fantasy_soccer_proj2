package com.ex.FantasySoccerLeague;
import com.ex.FantasySoccerLeague.Controller.FrontController;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;
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
public class LoginTest {

    @Mock
  //  private BookService bookService;
    private ApplicationServices login;

    @InjectMocks
   // private BookController bookController;
    private FrontController frontController;


    private MockMvc mockMvc;

    //private Book testBook;
    private Fantasy_User testUser;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

      //  mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc = MockMvcBuilders.standaloneSetup(frontController).build();


        testUser = new Fantasy_User();
        testUser.setEmail("Vincent.Bolden.12@cnu.edu");
        testUser.setPassword("pass");
//        testBook = new Book();
//        testBook.setIsbn("34517898-0");
//        testBook.setAuthor("Charlie Sheen");
//        testBook.setTitle("How To Win");
//        testBook.setPublishDate(LocalDate.of(2017, 4, 1));

//        when(bookService.getBook(17)).thenReturn(testBook);
        when(login.checkLogin("Vincent.Bolden.12@cnu.edu", "pass")).thenReturn(testUser);
    }

    @Test
    //happy path
    public void returnsAGoodLogin() throws Exception {
//        mockMvc.perform(get("/books/book/17"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isbn", is(testBook.getIsbn())));
        mockMvc.perform(post("/login")
                .content("{\"email\":\"Vincent.Bolden.12@cnu.edu\", \"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(testUser.getEmail())));
    }
}

