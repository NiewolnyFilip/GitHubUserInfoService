package pl.filip.niewolny.githubUser.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestCounterService {

    private final RequestCounterRepository requestCounterRepository;

    /**
     * Creates or increments the request counter for a user based on their login.
     *
     * @param login The login of the user.
     */
    public void createOrIncrementRequestCounter(String login) {
        requestCounterRepository.findByLogin(login)
                .ifPresentOrElse(requestCounter -> {
                    requestCounter.setRequestCount(requestCounter.getRequestCount() + 1);
                    requestCounterRepository.save(requestCounter);
                },
                () -> {
                    RequestCounter requestCounter = new RequestCounter();
                    requestCounter.setLogin(login);
                    requestCounter.setRequestCount(1);

                    requestCounterRepository.save(requestCounter);
                });
    }

    /**
     * Finds the request count for a user based on their login.
     *
     * @param login The login of the user.
     * @return The request count for the user. Returns 0 if the user does not exist.
     */
    public int findRequestCountByLogin(String login) {
        return requestCounterRepository.findByLogin(login)
                .map(RequestCounter::getRequestCount)
                .orElse(0);
    }
}
