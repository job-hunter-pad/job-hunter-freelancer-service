# Job Hunter Freelancer Service

## Environment Variables

- AUTH_VERIFICATION_URL

Example:
> AUTH_VERIFICATION_URL=http://localhost:8090/api/auth/validateId

`AUTH_VERIFICATION_URL` indicates the url to the Authentication Service

This Environment Variable is used to access the Authentication Service in order authorize certain requests

## Endpoints

### Get Active Jobs

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /activeJobs | /api/applications/activeJobs | GET |

#### Description

Active Jobs are those jobs with the status `PENDING`. This status indicates that an applicant has not been accepted yet

#### Request

List of JobOffers

JobOffer

```json
{
  "id": "",
  "jobName": "",
  "jobDescription": "",
  "date": "",
  "employerId": "",
  "employerName": "",
  "hourSalaryAmount": 0.0,
  "skills": [],
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

### Apply to Job

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /apply | /api/applications/apply | POST |

#### Notes

> Requires Authorization Header with JWT

#### Request

RequestBody: JobApplicationDTO

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

#### Response

JobApplication

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

### Complete a Job Application

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /complete/{freelancerId}/{jobId} | /api/applications/complete/{freelancerId}/{jobId} | POST |

#### Notes

> Requires Authorization Header with JWT

#### Request

PathVariable: freelancerId

PathVariable: jobId

#### Response

Job Application

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

### Get COMPLETED Job Offers of a Freelancer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /completedJobs/{freelancerId} | /api/applications/completedJobs/{freelancerId} | GET |

#### Description

Get Job Offers that belong to a Freelancer that have the status `COMPLETED`

#### Request

PathVariable: freelancerId

#### Response

List of Freelancer Job Offers

FreelancerJobOffer

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
    "skills": [],
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
      "status": ""
    }
  ]
}
```

```java
public enum JobOfferStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
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

### Get In Progress Job Offers of a Freelancer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /inProgressJobs/{freelancerId} | /api/applications/inProgressJobs/{freelancerId} | GET |

#### Description

Get Job Offers that belong to a Freelancer that have the status `IN_PROGRESS`

#### Request

PathVariable: freelancerId

#### Response

List of Freelancer Job Offers

FreelancerJobOffer

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
    "skills": [],
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
      "status": ""
    }
  ]
}
```

```java
public enum JobOfferStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
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

### Get All Job Applications of a Freelancer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /{freelancerId} | /api/applications/{freelancerId} | GET | 

#### Request

PathVariable: freelancerId

#### Response

List of Job Application

Job Application

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

### Get All Job Applications of a Job Off of a Freelancer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /{freelancerId}/{jobId} | /api/applications/{freelancerId}/{jobId} | GET | 

#### Request

PathVariable: freelancerId

PathVariable: jobId

#### Response

List of Job Application

Job Application

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

### Get All Jobs Offers that a Freelancer applied to

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /appliedJobs/{freelancerId} | /api/applications/appliedJobs/{freelancerId} | GET |

#### Request

PathVariable: freelancerId

#### Response

FreelancerJobsAndApplications

```json
{
  "id": "",
  "appliedJobOffers": [
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
        "skills": [],
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
          "status": ""
        }
      ]
    }
  ]
}
```

