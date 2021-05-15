package jobhunter.freelancerservice.repository;

import jobhunter.freelancerservice.model.JobApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobApplicationsRepository extends MongoRepository<JobApplication, String> {
}
