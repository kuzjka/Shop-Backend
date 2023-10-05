package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.RegisterDto;
import com.example.authserverresourceserversameapp.exception.UserExistsException;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void addUserTest() throws Exception {

        User user = new User();
        user.setUsername("user");
        given(userService.addUser(any(RegisterDto.class))).willReturn(user);
        this.mockMvc.perform(post("/register").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\": \"password\", \"passwordConfirmed\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("user with username: user successfully registered!")));
    }

    @Test
    @WithMockUser
    public void handleUserAlreadyExistsException() throws Exception {

        User user = new User();
        user.setUsername("user");
        given(userService.addUser(any(RegisterDto.class))).willThrow(new UserExistsException("User with username: user already exists!"));
        this.mockMvc.perform(post("/register").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\": \"password\", \"passwordConfirmed\": \"password\"}"))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("User with username: user already exists!")));
    }

    @Test
    @WithMockUser
    public void handlePasswordsDoNotMatchException() throws Exception {

        User user = new User();
        user.setUsername("user");
        given(userService.addUser(any(RegisterDto.class))).willThrow(new PasswordsDoNotMatchException());
        this.mockMvc.perform(post("/register").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\", \"password\": \"password1\", \"passwordConfirmed\": \"password2\"}"))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message", is("passwords do not match!")));
    }
}
