package jobhunter.freelancerservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JobApplication {
    private String jobId;
    private String freelancerId;
    private String freelancerName;
    private float hourSalaryAmount;
    private int estimatedProjectCompleteTime;
    private String message;
}
