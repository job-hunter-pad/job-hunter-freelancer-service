package jobhunter.freelancerservice.service.jobApplication;

import jobhunter.freelancerservice.controller.dto.JobApplicationDTO;
import jobhunter.freelancerservice.model.*;
import jobhunter.freelancerservice.repository.FreelancerAppliedJobsRepository;
import jobhunter.freelancerservice.repository.JobApplicationsRepository;
import jobhunter.freelancerservice.service.jobOffer.ActiveJobOffersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FreelancerApplicationServiceImpl implements FreelancerApplicationService {

    private final FreelancerAppliedJobsRepository freelancerAppliedJobsRepository;
    private final JobApplicationsRepository jobApplicationsRepository;
    private final ActiveJobOffersService activeJobOffersService;

    public FreelancerApplicationServiceImpl(FreelancerAppliedJobsRepository freelancerAppliedJobsRepository,
                                            JobApplicationsRepository jobApplicationsRepository,
                                            ActiveJobOffersService activeJobOffersService) {
        this.freelancerAppliedJobsRepository = freelancerAppliedJobsRepository;
        this.jobApplicationsRepository = jobApplicationsRepository;
        this.activeJobOffersService = activeJobOffersService;
    }

    @Override
    public Optional<JobApplication> applyToJob(JobApplicationDTO jobApplicationDTO) {

        String freelancerId = jobApplicationDTO.getFreelancerId();
        if (freelancerId == null || freelancerId.isEmpty()) {
            return Optional.empty();
        }

        Optional<JobOffer> jobOfferOptional = activeJobOffersService.getJobOffer(jobApplicationDTO.getJobId());

        if (jobOfferOptional.isEmpty()) {
            return Optional.empty();
        }
        JobOffer jobOffer = jobOfferOptional.get();

        FreelancerJobsAndApplications freelancerJobsAndApplications = freelancerAppliedJobsRepository.findById(freelancerId).orElseGet(() -> new FreelancerJobsAndApplications(freelancerId));

        List<FreelancerJobOffer> appliedJobOffers = freelancerJobsAndApplications.getAppliedJobOffers();
        int jobOfferIndex = -1;
        for (int i = 0; i < appliedJobOffers.size(); i++) {
            if (appliedJobOffers.get(i).getJobOffer().getId().equals(jobApplicationDTO.getJobId())) {
                jobOfferIndex = i;
                break;
            }
        }

        JobApplication jobApplication = new JobApplication(jobOffer.getId(),
                freelancerId, jobApplicationDTO.getFreelancerName(), jobApplicationDTO.getHourSalaryAmount(),
                jobApplicationDTO.getEstimatedProjectCompleteTime(), jobApplicationDTO.getMessage());
        FreelancerJobOffer freelancerJobOffer;

        jobApplication = jobApplicationsRepository.save(jobApplication);

        if (jobOfferIndex >= 0) {
            freelancerJobOffer = appliedJobOffers.get(jobOfferIndex);
            jobApplication = freelancerJobOffer.addApplication(jobApplication);
            freelancerJobOffer.setJobOffer(jobOffer);

            appliedJobOffers.set(jobOfferIndex, freelancerJobOffer);
        } else {
            freelancerJobOffer = new FreelancerJobOffer(jobOffer);
            jobApplication = freelancerJobOffer.addApplication(jobApplication);
            appliedJobOffers.add(freelancerJobOffer);
        }

        freelancerAppliedJobsRepository.save(freelancerJobsAndApplications);

        return Optional.of(jobApplication);
    }

    @Override
    public Optional<FreelancerJobsAndApplications> getAppliedJobOffers(String freelancerId) {
        return freelancerAppliedJobsRepository.findById(freelancerId);
    }

    @Override
    public List<JobApplication> getAllJobApplications(String freelancerId) {
        List<JobApplication> jobApplications = new ArrayList<>();

        Optional<FreelancerJobsAndApplications> optional = freelancerAppliedJobsRepository.findById(freelancerId);
        if (optional.isEmpty()) {
            return jobApplications;
        }

        FreelancerJobsAndApplications freelancerJobsAndApplications = optional.get();

        for (FreelancerJobOffer jobOffer : freelancerJobsAndApplications.getAppliedJobOffers()) {
            jobApplications.addAll(jobOffer.getApplications());
        }

        return jobApplications;
    }

    @Override
    public List<FreelancerJobOffer> getJobOffers(String freelancerId, JobOfferStatus status) {

        Optional<FreelancerJobsAndApplications> optional = freelancerAppliedJobsRepository.findById(freelancerId);
        if (optional.isEmpty()) {
            return new ArrayList<>();
        }

        return optional.get().getAppliedJobOffers().stream()
                .filter(freelancerJobOffer -> freelancerJobOffer.getJobOffer().getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public void updateApplication(JobApplication jobApplication) {
        Optional<FreelancerJobsAndApplications> optional = freelancerAppliedJobsRepository.findById(jobApplication.getFreelancerId());
        if (optional.isEmpty()) {
            return;
        }

        FreelancerJobsAndApplications freelancerJobsAndApplications = optional.get();
        Optional<FreelancerJobOffer> jobOfferOptional = freelancerJobsAndApplications.getAppliedJobOffers().stream()
                .filter(freelancerJobOffer -> freelancerJobOffer.getJobOffer().getId().equals(jobApplication.getJobId()))
                .findFirst();

        if (jobOfferOptional.isEmpty()) {
            return;
        }

        FreelancerJobOffer freelancerJobOffer = jobOfferOptional.get();
        List<JobApplication> applications = freelancerJobOffer.getApplications();

        for (int i = 0; i < applications.size(); i++) {
            JobApplication freelancerApplication = applications.get(i);

            if (freelancerApplication.getId().equals(jobApplication.getId())) {
                if (freelancerApplication.getStatus().equals(JobApplicationStatus.PENDING) || jobApplication.getStatus().equals(JobApplicationStatus.COMPLETED)) {
                    applications.set(i, jobApplication);
                }
                break;
            }
        }

        if (jobApplication.getStatus().equals(JobApplicationStatus.ACCEPTED)) {
            JobOffer jobOffer = freelancerJobOffer.getJobOffer();
            if (jobOffer.getStatus().equals(JobOfferStatus.PENDING)) {
                jobOffer.setStatus(JobOfferStatus.IN_PROGRESS);
            }
        }

        freelancerAppliedJobsRepository.save(freelancerJobsAndApplications);
    }

    @Override
    public void updateJobOffer(JobOffer jobOffer) {
        List<FreelancerJobsAndApplications> all = freelancerAppliedJobsRepository.findAll();

        for (FreelancerJobsAndApplications freelancerJobsAndApplications : all) {
            List<FreelancerJobOffer> appliedJobOffers = freelancerJobsAndApplications.getAppliedJobOffers();

            for (FreelancerJobOffer freelancerJobOffer : appliedJobOffers) {
                if (freelancerJobOffer.getJobOffer().getId().equals(jobOffer.getId())) {
                    freelancerJobOffer.setJobOffer(jobOffer);
                }
            }
            freelancerAppliedJobsRepository.save(freelancerJobsAndApplications);
        }
    }

    @Override
    public Optional<JobApplication> getAcceptedJobApplication(String freelancerId, String jobId) {

        Optional<FreelancerJobsAndApplications> optional = freelancerAppliedJobsRepository.findById(freelancerId);
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        FreelancerJobsAndApplications freelancerJobsAndApplications = optional.get();
        Optional<FreelancerJobOffer> jobOfferOptional = freelancerJobsAndApplications.getAppliedJobOffers().stream()
                .filter(freelancerJobOffer -> freelancerJobOffer.getJobOffer().getId().equals(jobId))
                .findFirst();

        if (jobOfferOptional.isEmpty()) {
            return Optional.empty();
        }

        FreelancerJobOffer freelancerJobOffer = jobOfferOptional.get();
        return freelancerJobOffer.getApplications().stream()
                .filter(jobApplication -> jobApplication.getStatus().equals(JobApplicationStatus.ACCEPTED))
                .findFirst();
    }

    @Override
    public List<JobApplication> getAllJobApplicationsOfAJob(String freelancerId, String jobId) {
        Optional<FreelancerJobsAndApplications> optional = freelancerAppliedJobsRepository.findById(freelancerId);
        if (optional.isEmpty()) {
            return new ArrayList<>();
        }

        FreelancerJobsAndApplications freelancerJobsAndApplications = optional.get();

        return freelancerJobsAndApplications.getAppliedJobOffers().stream()
                .filter(freelancerJobOffer -> freelancerJobOffer.getJobOffer().getId().equals(jobId))
                .map(FreelancerJobOffer::getApplications)
                .findFirst().orElse(new ArrayList<>());
    }
}
