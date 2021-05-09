package jobhunter.freelancerservice.service.jobOffer;

import jobhunter.freelancerservice.model.JobOffer;

import java.util.List;
import java.util.Optional;

public interface ActiveJobOffersService {
    void removeJobOffer(String jobOfferId);

    JobOffer updateJobOffer(JobOffer jobOffer);

    List<JobOffer> getAllJobOffers();

    Optional<JobOffer> getJobOffer(String jobOfferId);

}
