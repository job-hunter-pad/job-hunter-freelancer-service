package jobhunter.freelancerservice.interceptor;

public interface BearerExtractor {
    String extract(String header);
}