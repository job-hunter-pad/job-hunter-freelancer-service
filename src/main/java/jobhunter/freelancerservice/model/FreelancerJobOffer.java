package jobhunter.freelancerservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FreelancerJobOffer {

    private int applicationCount;
    private JobOffer jobOffer;
    private List<JobApplication> applications;

    public FreelancerJobOffer(JobOffer jobOffer) {
        applicationCount = 0;
        this.jobOffer = jobOffer;
        applications = new ArrayList<>();
    }

    public JobApplication addApplication(JobApplication jobApplication) {
        jobApplication.setId(String.valueOf(applicationCount));
        applications.add(jobApplication);
        applicationCount++;
        return jobApplication;
    }
}