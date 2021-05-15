package jobhunter.freelancerservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FreelancerJobOffer {

    private JobOffer jobOffer;
    private List<JobApplication> applications;

    public FreelancerJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
        applications = new ArrayList<>();
    }

    public JobApplication addApplication(JobApplication jobApplication) {
        applications.add(jobApplication);
        return jobApplication;
    }
}