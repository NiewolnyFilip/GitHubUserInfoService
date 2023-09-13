package pl.filip.niewolny.githubUser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import pl.filip.niewolny.githubUser.domain.RequestCounterService;
import pl.filip.niewolny.githubUser.dto.UserDto;
import pl.filip.niewolny.githubUser.dto.UserGitHubDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private RequestCounterService requestCounterService;
    @Mock
    private RestTemplate restTemplate;
    private UserService userService;

    @BeforeEach
    public void init() {
        userService = new UserService(requestCounterService, restTemplate);
        userService.setGithubApiUrl("");
    }

    @Test
    public void testGetUserInfo() {
        // Given login and mock response from github api
        String login = "octocat";

        when(restTemplate.getForObject(any(String.class), any()))
                .thenReturn(prepareGitHubDto(login));

        //when ask sevice for one login
        UserDto userDto = userService.getUserInfo(login);

        //then check userDto and request count for this login
        assertUserDto(userDto);
        int requestCountForLogin = requestCounterService.findRequestCountByLogin(login);
        Assertions.assertEquals(1, requestCountForLogin);

        // And ask again for same login
        userDto = userService.getUserInfo(login);

        //then check userDto and request count for this login
        assertUserDto(userDto);
        requestCountForLogin = requestCounterService.findRequestCountByLogin(login);
        Assertions.assertEquals(2, requestCountForLogin);
    }

    private void assertUserDto(UserDto userDto) {
        Assertions.assertEquals("octocat", userDto.getLogin());
        Assertions.assertEquals("Name", userDto.getName());
        Assertions.assertEquals(1L, userDto.getId());
        Assertions.assertEquals("type", userDto.getType());
        Assertions.assertEquals("url", userDto.getAvatarUrl());
        Assertions.assertEquals("url", userDto.getAvatarUrl());
        Assertions.assertEquals("2023", userDto.getCreatedAt());
        Assertions.assertEquals(3D, userDto.getCalculations(), 0.000d);
    }

    private UserGitHubDto prepareGitHubDto(String login) {
        return UserGitHubDto.builder()
                    .login(login)
                    .name("Name")
                    .id(1L)
                    .type("type")
                    .avatarUrl("url")
                    .createdAt("2023")
                    .followers(6)
                    .publicRepos(1)
                    .build();
    }
}