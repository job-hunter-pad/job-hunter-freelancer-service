# Job Hunter Employer Service

## Mappings

| Service URL | API Gateway URL | Method | Description |
| ------ | ------ | ------ | ------ |
| /activeJobs | /api/applications/activeJobs | GET | GET Active Jobs |
| /apply | /api/applications/apply | POST | Apply to Job Using with a Job Application |
| /completedJobs/{freelancerId} | /api/applications/completedJobs/{freelancerId} | GET | GET Completed Job Offers of a Freelancer with id |
| /inProgressJobs/{freelancerId} | /api/applications/inProgressJobs/{freelancerId} | GET | GET In Progress Job Offers of a Freelancer with id |
| /{freelancerId} | /api/applications/{freelancerId} | GET | Get All Job Applications of a Freelancer |
| /{freelancerId}/{jobId} | /api/applications/{freelancerId}/{jobId} | GET | Get All Job Applications of a Job Off of a Freelancer |
| /appliedJobs/{freelancerId} | /api/applications/appliedJobs/{freelancerId} | GET | Get All Jobs Offers that a Freelancer applied to |
| /complete/{freelancerId}/{jobId} | /api/applications/complete/{freelancerId}/{jobId} | POST | Complete a Job Application of a Job Offer |

## Mappings Request And Responses

### Requests JSON

#### Job Application DTO

```json
{
  "jobId": "",
  "freelancerId": "",
  "freelancerName": "",
  "hourSalaryAmount": 0.0,
  "estimatedProjectCompleteTime": 0,
  "message": ""
}
```

### Responses JSON

#### Job Offer

```json
{
  "id": "",
  "jobName": "",
  "jobDescription": "",
  "date": "",
  "employerId": "",
  "employerName": "",
  "hourSalaryAmount": 0.0,
  "status": ""
}
```

```java
public enum JobOfferStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}
```

#### Job Application

```json
{
  "id": "",
  "jobId": "",
  "freelancerId": "",
  "freelancerName": "",
  "hourSalaryAmount": 0.0,
  "estimatedProjectCompleteTime": 0,
  "message": "",
  "status": ""
}
```

```java
public enum JobApplicationStatus {
    ACCEPTED,
    COMPLETED,
    PENDING,
    REJECTED
}
```

#### Freelancer Job Offer

```json
 {
  "applicationCount": 0,
  "jobOffer": {
    "id": "",
    "jobName": "",
    "jobDescription": "",
    "date": "",
    "employerId": "",
    "employerName": "",
    "hourSalaryAmount": 0.0,
    "status": ""
  },
  "applications": [
    {
      "id": "",
      "jobId": "",
      "freelancerId": "",
      "freelancerName": "",
      "hourSalaryAmount": 0.0,
      "estimatedProjectCompleteTime": 0,
      "message": "",
      "status": "COMPLETED"
    }
  ]
}
```

```java
  public class FreelancerJobOffer {

    private int applicationCount;
    private JobOffer jobOffer;
    private List<JobApplication> applications;
}
```