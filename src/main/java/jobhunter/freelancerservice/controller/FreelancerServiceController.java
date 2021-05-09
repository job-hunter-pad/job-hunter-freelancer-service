package jobhunter.freelancerservice.controller;

import jobhunter.freelancerservice.controller.dto.JobApplicationDTO;
import jobhunter.freelancerservice.kafka.producer.JobApplicationsProducer;
import jobhunter.freelancerservice.model.*;
import jobhunter.freelancerservice.service.jobApplication.FreelancerApplicationService;
import jobhunter.freelancerservice.service.jobOffer.ActiveJobOffersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class FreelancerServiceController {

    private final JobApplicationsProducer jobApplicationsProducer;

    private final ActiveJobOffersService activeJobOffersService;
    private final FreelancerApplicationService freelancerApplicationService;

    public FreelancerServiceController(JobApplicationsProducer jobApplicationsProducer,
                                       ActiveJobOffersService activeJobOffersService,
                                       FreelancerApplicationService freelancerApplicationService) {
        this.jobApplicationsProducer = jobApplicationsProducer;
        this.activeJobOffersService = activeJobOffersService;
        this.freelancerApplicationService = freelancerApplicationService;
    }

    @GetMapping("/activeJobs")
    public List<JobOffer> getAllActiveJobOffers() {
        return activeJobOffersService.getAllJobOffers();
    }

    @PostMapping("/apply")
    public JobApplication applyToJob(@RequestBody JobApplicationDTO newJobApplication) {
        Optional<JobApplication> freelancerApplicationsOptional = freelancerApplicationService.applyToJob(newJobApplication);

        if (freelancerApplicationsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        JobApplication jobApplication = freelancerApplicationsOptional.get();

        jobApplicationsProducer.postJobApplication(jobApplication);

        return jobApplication;
    }

    @GetMapping("/completedJobs/{freelancerId}")
    public List<FreelancerJobOffer> getCompletedJobOffers(@PathVariable String freelancerId) {
        return freelancerApplicationService.getJobOffers(freelancerId, JobOfferStatus.COMPLETED);
    }

    @GetMapping("/inProgressJobs/{freelancerId}")
    public List<FreelancerJobOffer> getInProgressJobOffers(@PathVariable String freelancerId) {
        return freelancerApplicationService.getJobOffers(freelancerId, JobOfferStatus.IN_PROGRESS);
    }

    @GetMapping("/applications/{freelancerId}/{jobId}")
    public List<JobApplication> getFreelancerApplicationsOfAJob(@PathVariable String freelancerId, @PathVariable String jobId) {
        return freelancerApplicationService.getAllJobApplicationsOfAJob(freelancerId, jobId);
    }

    @GetMapping("/applications/{freelancerId}")
    public List<JobApplication> getFreelancerApplications(@PathVariable String freelancerId) {
        return freelancerApplicationService.getAllJobApplications(freelancerId);
    }

    @GetMapping("/appliedJobs/{freelancerId}")
    public FreelancerJobsAndApplications getAppliedJobsOfFreelancer(@PathVariable String freelancerId) {
        return freelancerApplicationService.getAppliedJobOffers(freelancerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/complete/{freelancerId}/{jobId}")
    public JobApplication completeJobOffer(@PathVariable String freelancerId, @PathVariable String jobId) {
        Optional<JobApplication> jobApplicationOptional = freelancerApplicationService.getAcceptedJobApplication(freelancerId, jobId);
        if (jobApplicationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        JobApplication jobApplication = jobApplicationOptional.get();

        jobApplication.setStatus(JobApplicationStatus.COMPLETED);

        freelancerApplicationService.updateApplication(jobApplication);

        jobApplicationsProducer.postJobApplication(jobApplication);

        return jobApplication;
    }
}
