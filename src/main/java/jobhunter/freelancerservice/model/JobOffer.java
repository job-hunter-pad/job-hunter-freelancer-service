package jobhunter.freelancerservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class JobOffer {
    @Id
    private String id;

    private String jobName;
    private String jobDescription;
    private String date;
    private String employerId;
    private String employerName;
    private float hourSalaryAmount;
    private JobOfferStatus status;
}
