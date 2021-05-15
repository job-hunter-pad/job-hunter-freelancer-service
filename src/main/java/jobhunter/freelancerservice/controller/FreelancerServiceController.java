package jobhunter.freelancerservice.controller;

import jobhunter.freelancerservice.controller.authorization.AuthTokenValidator;
import jobhunter.freelancerservice.controller.dto.JobApplicationDTO;
import jobhunter.freelancerservice.interceptor.AuthTokenHTTPInterceptor;
import jobhunter.freelancerservice.interceptor.BearerExtractor;
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

    private final AuthTokenValidator authTokenValidator;
    private final BearerExtractor bearerExtractor;

    public FreelancerServiceController(JobApplicationsProducer jobApplicationsProducer,
                                       ActiveJobOffersService activeJobOffersService,
                                       FreelancerApplicationService freelancerApplicationService,
                                       AuthTokenValidator authTokenValidator,
                                       BearerExtractor bearerExtractor) {
        this.jobApplicationsProducer = jobApplicationsProducer;
        this.activeJobOffersService = activeJobOffersService;
        this.freelancerApplicationService = freelancerApplicationService;
        this.authTokenValidator = authTokenValidator;
        this.bearerExtractor = bearerExtractor;
    }

    @GetMapping("/{freelancerId}")
    public List<JobApplication> getFreelancerApplications(@PathVariable String freelancerId) {
        return freelancerApplicationService.getAllJobApplications(freelancerId);
    }

    @GetMapping("/{freelancerId}/{jobId}")
    public List<JobApplication> getFreelancerApplicationsOfAJob(@PathVariable String freelancerId, @PathVariable String jobId) {
        return freelancerApplicationService.getAllJobApplicationsOfAJob(freelancerId, jobId);
    }

    @GetMapping("/activeJobs")
    public List<JobOffer> getAllActiveJobOffers() {
        return activeJobOffersService.getAllJobOffers();
    }

    @PostMapping("/apply")
    public JobApplication applyToJob(@RequestBody JobApplicationDTO newJobApplication, @RequestHeader(AuthTokenHTTPInterceptor.AUTHORIZATION_HEADER) String header) {

        if (!authTokenValidator.authorize(newJobApplication.getFreelancerId(), bearerExtractor.extract(header))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

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

    @GetMapping("/appliedJobs/{freelancerId}")
    public FreelancerJobsAndApplications getAppliedJobsOfFreelancer(@PathVariable String freelancerId) {
        return freelancerApplicationService.getAppliedJobOffers(freelancerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/complete/{freelancerId}/{jobId}")
    public JobApplication completeJobOffer(@PathVariable String freelancerId, @PathVariable String jobId, @RequestHeader(AuthTokenHTTPInterceptor.AUTHORIZATION_HEADER) String header) {

        if (!authTokenValidator.authorize(freelancerId, bearerExtractor.extract(header))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Optional<JobApplication> jobApplicationOptional = freelancerApplicationService.getAcceptedJobApplication(freelancerId, jobId);
        if (jobApplicationOptional.isEmpty()) {

            Optional<FreelancerJobsAndApplications> freelancerJobsAndApplications = freelancerApplicationService.getAppliedJobOffers(freelancerId);
            if (freelancerJobsAndApplications.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            List<FreelancerJobOffer> appliedJobOffers = freelancerJobsAndApplications.get().getAppliedJobOffers();
            Optional<FreelancerJobOffer> freelancerJobOfferOptional = appliedJobOffers.stream().filter(freelancerJobOffer -> freelancerJobOffer.getJobOffer().getId().equals(jobId))
                    .findFirst();
            if (freelancerJobOfferOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            FreelancerJobOffer freelancerJobOffer = freelancerJobOfferOptional.get();
            if (!freelancerJobOffer.getJobOffer().getStatus().equals(JobOfferStatus.PENDING)) {
                jobApplicationOptional = freelancerJobOffer.getApplications().stream()
                        .filter(jobApplication -> !jobApplication.getStatus().equals(JobApplicationStatus.REJECTED))
                        .findFirst();
            }
            if (jobApplicationOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }

        JobApplication jobApplication = jobApplicationOptional.get();

        jobApplication.setStatus(JobApplicationStatus.COMPLETED);

        freelancerApplicationService.updateApplication(jobApplication);

        jobApplicationsProducer.postJobApplication(jobApplication);

        return jobApplication;
    }
}
