package jobhunter.freelancerservice.kafka.producer;

import jobhunter.freelancerservice.model.JobApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationsProducer {

    private final KafkaTemplate<String, JobApplication> jobApplicationsKafkaTemplate;

    private static final String TOPIC = "job_application";

    public JobApplicationsProducer(KafkaTemplate<String, JobApplication> jobApplicationsKafkaTemplate) {
        this.jobApplicationsKafkaTemplate = jobApplicationsKafkaTemplate;
    }

    public String postJobApplication(JobApplication jobApplication) {
        jobApplicationsKafkaTemplate.send(TOPIC, jobApplication);
        return "Published successfully";
    }
}
