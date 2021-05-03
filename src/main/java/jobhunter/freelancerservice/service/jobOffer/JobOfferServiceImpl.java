package jobhunter.freelancerservice.service.jobOffer;

import jobhunter.freelancerservice.model.JobOffer;
import jobhunter.freelancerservice.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    @Override
    public void removeJobOffer(String jobOfferId) {
        jobOfferRepository.deleteById(jobOfferId);
    }

    @Override
    public JobOffer updateJobOffer(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    @Override
    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }
}
