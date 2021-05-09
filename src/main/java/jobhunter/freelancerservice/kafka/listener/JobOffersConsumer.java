package jobhunter.freelancerservice.kafka.listener;

import jobhunter.freelancerservice.model.JobOffer;
import jobhunter.freelancerservice.model.JobOfferStatus;
import jobhunter.freelancerservice.service.jobApplication.FreelancerApplicationService;
import jobhunter.freelancerservice.service.jobOffer.ActiveJobOffersService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JobOffersConsumer {

    private final ActiveJobOffersService activeJobOffersService;
    private final FreelancerApplicationService freelancerApplicationService;

    public JobOffersConsumer(ActiveJobOffersService activeJobOffersService,
                             FreelancerApplicationService freelancerApplicationService) {
        this.activeJobOffersService = activeJobOffersService;
        this.freelancerApplicationService = freelancerApplicationService;
    }

    @KafkaListener(topics = "jobs", groupId = "group_joboffer_freelancer", containerFactory = "jobOfferKafkaListenerContainerFactory")
//    @KafkaListener(topicPartitions = {
//            @TopicPartition(topic = "jobs", partitionOffsets = @PartitionOffset(initialOffset = "0", partition = "0"))
//    }, groupId = "group_joboffer_freelancer", containerFactory = "kafkaListenerContainerFactory")
    public void consumeJobOffer(JobOffer jobOffer) {
        freelancerApplicationService.updateJobOffer(jobOffer);
        if (jobOffer.getStatus() != JobOfferStatus.PENDING) {
            activeJobOffersService.removeJobOffer(jobOffer.getId());
            return;
        }
        activeJobOffersService.updateJobOffer(jobOffer);
    }
}
