package pl.filip.niewolny.githubUser.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.filip.niewolny.githubUser.dto.UserDto;
import pl.filip.niewolny.githubUser.dto.UserGitHubDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverters {

    public static UserDto convertToUserDto(UserGitHubDto userGitHubDto) {
        return UserDto.builder()
                .id(userGitHubDto.getId())
                .type(userGitHubDto.getType())
                .avatarUrl(userGitHubDto.getAvatarUrl())
                .login(userGitHubDto.getLogin())
                .name(userGitHubDto.getName())
                .createdAt(userGitHubDto.getCreatedAt())
                .calculations(calulactions(userGitHubDto.getFollowers(), userGitHubDto.getPublicRepos()))
                .build();
    }

    private static Double calulactions(Integer followers, Integer publicRepos) {
        if (followers == null || publicRepos == null)
            return 0d;

        return 6D / followers * (2 + publicRepos);
    }
}
