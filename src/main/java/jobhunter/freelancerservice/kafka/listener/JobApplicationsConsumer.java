package jobhunter.freelancerservice.kafka.listener;

import jobhunter.freelancerservice.model.JobApplication;
import jobhunter.freelancerservice.service.jobApplication.FreelancerApplicationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationsConsumer {

    private final FreelancerApplicationService freelancerApplicationService;

    public JobApplicationsConsumer(FreelancerApplicationService freelancerApplicationService) {
        this.freelancerApplicationService = freelancerApplicationService;
    }


    @KafkaListener(topics = "job_application", groupId = "group_job_application_freelancer", containerFactory = "jobApplicationsKafkaListenerContainerFactory")
    public void consumeJobApplication(JobApplication jobApplication) {
        System.out.println("Consumed JSON Message: " + jobApplication);
        freelancerApplicationService.updateApplication(jobApplication);
    }

}
