package jobhunter.freelancerservice.service.jobApplication;

import jobhunter.freelancerservice.controller.dto.JobApplicationDTO;
import jobhunter.freelancerservice.model.*;

import java.util.List;
import java.util.Optional;

public interface FreelancerApplicationService {
    Optional<JobApplication> applyToJob(JobApplicationDTO jobApplicationDTO);

    Optional<FreelancerJobsAndApplications> getAppliedJobOffers(String freelancerId);

    List<JobApplication> getAllJobApplications(String freelancerId);

    List<FreelancerJobOffer> getJobOffers(String freelancerId, JobOfferStatus status);

    Optional<JobApplication> getAcceptedJobApplication(String freelancerId, String jobId);

    void updateApplication(JobApplication jobApplication);

    void updateJobOffer(JobOffer jobOffer);

    List<JobApplication> getAllJobApplicationsOfAJob(String freelancerId, String jobId);
}
