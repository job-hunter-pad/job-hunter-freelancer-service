package jobhunter.freelancerservice.service.jobApplication;

import jobhunter.freelancerservice.model.FreelancerApplications;
import jobhunter.freelancerservice.model.JobApplication;
import jobhunter.freelancerservice.model.JobOffer;
import jobhunter.freelancerservice.repository.FreelancerJobApplicationRepository;
import jobhunter.freelancerservice.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final FreelancerJobApplicationRepository freelancerJobApplicationRepository;
    private final JobOfferRepository jobOfferRepository;

    public JobApplicationServiceImpl(FreelancerJobApplicationRepository freelancerJobApplicationRepository, JobOfferRepository jobOfferRepository) {
        this.freelancerJobApplicationRepository = freelancerJobApplicationRepository;
        this.jobOfferRepository = jobOfferRepository;
    }

    @Override
    public Optional<FreelancerApplications> applyToJob(JobApplication jobApplication) {

        Optional<JobOffer> jobOffer = jobOfferRepository.findById(jobApplication.getJobId());
        if (jobOffer.isEmpty()) {
            return Optional.empty();
        }
        Optional<FreelancerApplications> freelancerApplicationsOptional = freelancerJobApplicationRepository.findById(jobApplication.getFreelancerId());

        FreelancerApplications freelancerApplications = freelancerApplicationsOptional.orElseGet(FreelancerApplications::new);
        freelancerApplications.addApplication(jobApplication);

        freelancerJobApplicationRepository.save(freelancerApplications);

        return Optional.of(freelancerApplications);
    }

    @Override
    public Optional<FreelancerApplications> getAllApplicationsOfFreelancer(String freelancerId) {
        return freelancerJobApplicationRepository.findById(freelancerId);
    }
}
