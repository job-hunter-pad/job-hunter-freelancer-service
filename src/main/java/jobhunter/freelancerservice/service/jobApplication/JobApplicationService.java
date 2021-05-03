package jobhunter.freelancerservice.service.jobApplication;

import jobhunter.freelancerservice.model.FreelancerApplications;
import jobhunter.freelancerservice.model.JobApplication;

import java.util.Optional;

public interface JobApplicationService {
    Optional<FreelancerApplications> applyToJob(JobApplication jobApplicationDTO);

    Optional<FreelancerApplications> getAllApplicationsOfFreelancer(String freelancerId);
}
