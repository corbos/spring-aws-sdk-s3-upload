# Upload a File to a S3 Bucket

## Requirements

- Spring Boot/MVC
- The Amazon Java SDK 2.0

(See `pom.xml`.)

## Mechanisms

### Front-end

Browse to `src/main/resources/static`. These assets are served statically by the Tomcat HTTP server. 

Example:
- http://localhost:8080 resolves to `index.html`.
- http://localhost:8080/index.js resolves to `index.js`.
- http://localhost:8080/index.css resolves to `index.css`.

The `index.js` file listens for changes on the file input in `index.html`. Once a file is selected, it can be submitted to the Java backend (ImageUploadController.upload).

### Back-end

Open the `ImageUploadController` class.

First we must create an `S3Client` instance with the correct region and credentials. The beautiful thing about credentials is that there's a credential provider that tries multiple strategies:

https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials.html#credentials-chain

In my case, I set two environment variables:
1. `AWS_ACCESS_KEY_ID`
2. `AWS_SECRET_ACCESS_KEY`

So you must have an IAM user with appropriate permissions that has an Access Key and Secret Access Key.

Create a `PutObjectRequest`.

Then use the `MultipartFile`'s bytes to send the file's bytes S3.