package jobhunter.freelancerservice.controller;

import jobhunter.freelancerservice.kafka.producer.JobApplicationsProducer;
import jobhunter.freelancerservice.model.FreelancerApplications;
import jobhunter.freelancerservice.model.JobApplication;
import jobhunter.freelancerservice.model.JobOffer;
import jobhunter.freelancerservice.service.jobApplication.JobApplicationService;
import jobhunter.freelancerservice.service.jobOffer.JobOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class JobApplicationsController {

    private final JobApplicationsProducer jobApplicationsProducer;

    private final JobOfferService jobOfferService;
    private final JobApplicationService jobApplicationService;

    public JobApplicationsController(JobApplicationsProducer jobApplicationsProducer, JobOfferService jobOfferService, JobApplicationService jobApplicationService) {
        this.jobApplicationsProducer = jobApplicationsProducer;
        this.jobOfferService = jobOfferService;
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping("/")
    public List<JobOffer> getAllActiveJobOffers() {
        return jobOfferService.getAllJobOffers();
    }

    @PostMapping("/apply")
    public FreelancerApplications applyToJob(@RequestBody JobApplication newJobApplication) {
        Optional<FreelancerApplications> freelancerApplicationsOptional = jobApplicationService.applyToJob(newJobApplication);

        if (freelancerApplicationsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        jobApplicationsProducer.postJobApplication(newJobApplication);

        return freelancerApplicationsOptional.get();
    }

    @GetMapping("/{freelancerId}")
    public FreelancerApplications getFreelancerApplications(@PathVariable String freelancerId) {
        return jobApplicationService.getAllApplicationsOfFreelancer(freelancerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
