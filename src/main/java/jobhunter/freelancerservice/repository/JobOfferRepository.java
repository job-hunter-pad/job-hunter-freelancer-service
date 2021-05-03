package jobhunter.freelancerservice.repository;

import jobhunter.freelancerservice.model.JobOffer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobOfferRepository extends MongoRepository<JobOffer, String> {
}
