package jobhunter.freelancerservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JobApplicationDTO {
    private String jobId;
    private String freelancerId;
    private String freelancerName;
    private float hourSalaryAmount;
    private int estimatedProjectCompleteTime;
    private String message;
}
