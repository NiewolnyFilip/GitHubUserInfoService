package pl.filip.niewolny.githubUser.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.filip.niewolny.githubUser.converters.UserConverters;
import pl.filip.niewolny.githubUser.domain.RequestCounterService;
import pl.filip.niewolny.githubUser.dto.UserDto;
import pl.filip.niewolny.githubUser.dto.UserGitHubDto;
import pl.filip.niewolny.githubUser.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RequestCounterService requestCounterService;
    private final RestTemplate restTemplate;

    @Value("${github.api.url}")
    @Setter(value = AccessLevel.PACKAGE)
    private String githubApiUrl;

    /**
     * Retrieves user information from the GitHub API and converts it into a UserDto object.
     *
     * @param login The GitHub username of the user whose information is to be retrieved.
     * @return UserDto containing user information.
     * @throws UserNotFoundException if the user does not exist on GitHub.
     */
    public UserDto getUserInfo(String login) {
        // Increment the request counter for the provided GitHub user login.
        requestCounterService.createOrIncrementRequestCounter(login);

        // Construct the URL to fetch user data from the GitHub API.
        String apiUrl = githubApiUrl + login;

        // Send a GET request to the GitHub API to retrieve user data.
        UserGitHubDto userGitHubDto = restTemplate.getForObject(apiUrl, UserGitHubDto.class);

        // Check if userGitHubDto is null, indicating that the user does not exist.
        if (userGitHubDto == null) {
            throw new UserNotFoundException(login);
        }

        // Convert the fetched GitHub user data to a UserDto object.
        return UserConverters.convertToUserDto(userGitHubDto);
    }
}

