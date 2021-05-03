package jobhunter.freelancerservice.repository;

import jobhunter.freelancerservice.model.FreelancerApplications;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FreelancerJobApplicationRepository extends MongoRepository<FreelancerApplications, String> {
}
