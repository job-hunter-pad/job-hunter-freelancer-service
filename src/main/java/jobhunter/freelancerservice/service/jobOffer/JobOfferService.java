package jobhunter.freelancerservice.service.jobOffer;

import jobhunter.freelancerservice.model.JobOffer;

import java.util.List;

public interface JobOfferService {
    void removeJobOffer(String jobOfferId);

    JobOffer updateJobOffer(JobOffer jobOffer);

    List<JobOffer> getAllJobOffers();
}
