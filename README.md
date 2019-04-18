Spring Boot Demo Application that retrieves dog data
from an API, stores the data in an own DB and saves a corresponding picture in AWS S3 bucket.
 Following endpoints can be used:


**Create dog entry from API in db and store corresponding picture in aws s3**

    PUT /api/v1/dog

**Get dog entry by id**

    GET /api/v1/dog/{id}
    
**Delete dog entry by id (also deletes picture in aws s3)**

    DELETE /api/v1/dog/{id}
**Get all dog entries filtered by breed**

    GET /api/v1/dog/breed/{breed}
    
  **Get all distinct breeds**

    GET /api/v1/dog/breed/  
