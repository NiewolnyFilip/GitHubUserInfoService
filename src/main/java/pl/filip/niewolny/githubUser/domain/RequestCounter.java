package pl.filip.niewolny.githubUser.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
class RequestCounter {
    @Id
    private String login;
    private int requestCount;
}
