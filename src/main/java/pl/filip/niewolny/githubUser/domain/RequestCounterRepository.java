package pl.filip.niewolny.githubUser.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface RequestCounterRepository extends CrudRepository<RequestCounter, String> {
    Optional<RequestCounter> findByLogin(String login);
}
