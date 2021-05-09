package jobhunter.freelancerservice.repository;

import jobhunter.freelancerservice.model.FreelancerJobsAndApplications;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FreelancerAppliedJobsRepository extends MongoRepository<FreelancerJobsAndApplications, String> {
}
