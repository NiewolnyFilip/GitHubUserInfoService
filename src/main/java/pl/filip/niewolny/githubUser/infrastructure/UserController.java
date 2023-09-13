package pl.filip.niewolny.githubUser.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.filip.niewolny.githubUser.dto.UserDto;
import pl.filip.niewolny.githubUser.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{login}")
    public UserDto getUser(@PathVariable String login) {
        return userService.getUserInfo(login);
    }
}
