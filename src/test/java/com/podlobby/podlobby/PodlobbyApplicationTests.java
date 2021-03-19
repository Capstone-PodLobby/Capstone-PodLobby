package com.podlobby.podlobby;

import com.podlobby.podlobby.model.*;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PodlobbyApplication.class)
@AutoConfigureMockMvc
class PodlobbyApplicationTests {


    private User testUser;
    private HttpSession httpSession;
    private Podcast podcast;
    private Category category;
    private Comment comment;
    private Request request;
    private Response response;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PodcastRepository podcastDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {



//        httpSession = this.mvc.perform(
//                post("/login").with(csrf())
//                        .param("username", "testUser")
//                        .param("password", "pass"))
//                .andExpect(status().is(HttpStatus.FOUND.value()))
//                .andExpect(redirectedUrl("/"))
//                .andReturn()
//                .getRequest()
//                .getSession();
    }



    @Test
    void contextLoads() {
    }

}
