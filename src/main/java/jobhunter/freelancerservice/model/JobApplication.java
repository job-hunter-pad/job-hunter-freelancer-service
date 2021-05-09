package jobhunter.freelancerservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JobApplication {
    private String id;
    private String jobId;
    private String freelancerId;
    private String freelancerName;
    private float hourSalaryAmount;
    private int estimatedProjectCompleteTime;
    private String message;
    private JobApplicationStatus status;

    public JobApplication(String jobId, String freelancerId, String freelancerName, float hourSalaryAmount, int estimatedProjectCompleteTime, String message) {
        this.jobId = jobId;
        this.freelancerId = freelancerId;
        this.freelancerName = freelancerName;
        this.hourSalaryAmount = hourSalaryAmount;
        this.estimatedProjectCompleteTime = estimatedProjectCompleteTime;
        this.message = message;
        status = JobApplicationStatus.PENDING;
    }
}
