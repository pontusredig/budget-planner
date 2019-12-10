package se.yrgo.budgetplanner.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TestUserRestController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void createUserTest(){

    }

    @Test
    public void getUserById() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .get("/users/get/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1));
    }
}
