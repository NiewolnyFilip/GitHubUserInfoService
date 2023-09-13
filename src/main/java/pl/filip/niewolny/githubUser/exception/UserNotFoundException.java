package pl.filip.niewolny.githubUser.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super("Użytkownik o loginie '" + login + "' nie został znaleziony na githubie.");
    }
}
