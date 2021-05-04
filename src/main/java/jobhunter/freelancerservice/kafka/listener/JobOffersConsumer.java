package jobhunter.freelancerservice.kafka.listener;

import jobhunter.freelancerservice.model.JobOffer;
import jobhunter.freelancerservice.model.JobOfferStatus;
import jobhunter.freelancerservice.service.jobOffer.JobOfferService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JobOffersConsumer {

    private final JobOfferService jobOfferService;

    public JobOffersConsumer(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @KafkaListener(topics = "jobs", groupId = "group_joboffer_freelancer", containerFactory = "kafkaListenerContainerFactory")
//    @KafkaListener(topicPartitions = {
//            @TopicPartition(topic = "jobs", partitionOffsets = @PartitionOffset(initialOffset = "0", partition = "0"))
//    }, groupId = "group_joboffer_freelancer", containerFactory = "kafkaListenerContainerFactory")
    public void consumeJobOffer(JobOffer jobOffer) {
        if (jobOffer.getStatus() != JobOfferStatus.PENDING) {
            jobOfferService.removeJobOffer(jobOffer.getId());
            return;
        }
        jobOfferService.updateJobOffer(jobOffer);
    }
}
