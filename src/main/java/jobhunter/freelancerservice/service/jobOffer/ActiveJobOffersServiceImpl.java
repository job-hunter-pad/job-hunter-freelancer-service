package jobhunter.freelancerservice.service.jobOffer;

import jobhunter.freelancerservice.model.JobOffer;
import jobhunter.freelancerservice.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActiveJobOffersServiceImpl implements ActiveJobOffersService {

    private final JobOfferRepository jobOfferRepository;

    public ActiveJobOffersServiceImpl(JobOfferRepository jobOfferRepository) {
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

    @Override
    public Optional<JobOffer> getJobOffer(String jobOfferId) {
        return jobOfferRepository.findById(jobOfferId);
    }
}
