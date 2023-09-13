package pl.filip.niewolny.githubUser.infrastructure;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.filip.niewolny.githubUser.dto.UserDto;
import pl.filip.niewolny.githubUser.exception.UserNotFoundException;
import pl.filip.niewolny.githubUser.service.UserService;

import static org.mockito.ArgumentMatchers.anyString;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUser_Success() throws Exception {
        // Given
        // Prepare a mock UserDto response
        UserDto userDto = UserDto.builder()
                .id(123L)
                .login("test")
                .name("name")
                .createdAt("created")
                .avatarUrl("url")
                .type("type")
                .calculations(12D)
                .build();

        Mockito.when(userService.getUserInfo(anyString())).thenReturn(userDto);

        // Then
        // Perform a GET request to /users/{login} with a valid username
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{login}", "testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(123L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avatar_url").value("url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at").value("created"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("type"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.calculations").value(12D));
    }

    @Test
    public void testGetUser_UserNotFound() throws Exception {
        // Given
        // Mock the UserService to throw UserNotFoundException when a specific username is requested
        Mockito.when(userService.getUserInfo("nonExistingUser")).thenThrow(UserNotFoundException.class);

        // Then
        // Perform a GET request to /users/{login} with a non-existing username
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{login}", "nonExistingUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}