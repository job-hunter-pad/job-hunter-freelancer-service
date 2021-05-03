package jobhunter.freelancerservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@NoArgsConstructor
@Data
public class FreelancerApplications {
    @Id
    private String id;

    private List<JobApplication> jobApplications;

    public void addApplication(JobApplication jobApplication) {
        jobApplications.add(jobApplication);
    }
}
