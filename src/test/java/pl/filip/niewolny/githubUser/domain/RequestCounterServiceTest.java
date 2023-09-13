package pl.filip.niewolny.githubUser.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class RequestCounterServiceTest {

    @MockBean
    private RequestCounterRepository requestCounterRepository;

    private RequestCounterService requestCounterService;

    @BeforeEach
    public void setUp() {
        requestCounterService = new RequestCounterService(requestCounterRepository);
    }

    @Test
    public void testCreateOrIncrementRequestCounter_UserExists() {
        // Given
        // Preparation of test data
        String login = "testUser";
        RequestCounter existingCounter = new RequestCounter();
        existingCounter.setLogin(login);
        existingCounter.setRequestCount(3);

        Mockito.when(requestCounterRepository.findByLogin(login)).thenReturn(Optional.of(existingCounter));

        // When
        // Calling the tested method
        requestCounterService.createOrIncrementRequestCounter(login);

        // Then
        Mockito.verify(requestCounterRepository).save(existingCounter);
        Assertions.assertEquals(4, existingCounter.getRequestCount());

    }

    @Test
    public void testCreateOrIncrementRequestCounter_UserDoesNotExist() {
        // Given
        // Preparation of test data
        String login = "newUser";

        Mockito.when(requestCounterRepository.findByLogin(login)).thenReturn(java.util.Optional.empty());

        // When
        // Calling the tested method
        requestCounterService.createOrIncrementRequestCounter(login);

        // Then
        Mockito.verify(requestCounterRepository).save(Mockito.any(RequestCounter.class));
    }

    @Test
    public void testFindRequestCountByLogin_UserExists() {
        // Given
        // Preparation of test data
        String login = "testUser";
        RequestCounter existingCounter = new RequestCounter();
        existingCounter.setLogin(login);
        existingCounter.setRequestCount(3);

        Mockito.when(requestCounterRepository.findByLogin(login)).thenReturn(java.util.Optional.of(existingCounter));

        // When
        // Calling the tested method
        int requestCount = requestCounterService.findRequestCountByLogin(login);

        Assertions.assertEquals(3, requestCount);
    }

    @Test
    public void testFindRequestCountByLogin_UserDoesNotExist() {
        // Given
        // Preparation of test data
        String login = "nonExistingUser";

        Mockito.when(requestCounterRepository.findByLogin(login)).thenReturn(java.util.Optional.empty());

        // When
        // Calling the tested method
        int requestCount = requestCounterService.findRequestCountByLogin(login);

        Assertions.assertEquals(0, requestCount);
    }
}