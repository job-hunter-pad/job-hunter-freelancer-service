package jobhunter.freelancerservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class FreelancerJobsAndApplications {
    @Id
    private String id;

    private List<FreelancerJobOffer> appliedJobOffers;

    public FreelancerJobsAndApplications(String id) {
        this.id = id;
        appliedJobOffers = new ArrayList<>();
    }
}
