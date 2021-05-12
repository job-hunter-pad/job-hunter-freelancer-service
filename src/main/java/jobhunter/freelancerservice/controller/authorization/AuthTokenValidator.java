package jobhunter.freelancerservice.controller.authorization;

public interface AuthTokenValidator {
    boolean authorize(String id, String token);
}
