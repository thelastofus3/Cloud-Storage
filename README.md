# Cloud Storage Application
Welcome to the Cloud Storage Application! This README provides an overview of the application's functionality and architecture.

## Registration
New users can register for an account by providing necessary details like username, email, and password.

## Authentication
Registered users can log in to their accounts using their username and password.

## OAuth2 Authentication with GitHub, Facebook, and Google
The application supports OAuth2 authentication, allowing users to sign in using their existing accounts from GitHub, Facebook, or Google. OAuth2 is a widely used authentication protocol that enables secure access to user data without sharing their credentials with the application.

## Logout
Logged-in users can log out of their accounts to terminate their session.

## File and Folder Operations
### File and Folder Manipulation
-  Upload: Users can upload files and folders to their storage space.
-  Create Folder: Users can create new empty folders similar to creating a new folder in a file explorer.
-  Delete: Users can delete files and folders from their storage space.
-  Rename: Users can rename files and folders.
-  Download: Users can download files and folders.
## Application Interface
### Home Page
#### Header:
- For unauthenticated users: Registration and authentication buttons.
- For authenticated users: Display the current user's login and a logout button.
#### Content (for authenticated users):
- Search form for files and folders by name.
- Breadcrumbs showing the path from the root folder to the current folder. Each element is a link to its respective folder.
- List of files in the current directory. For each file, display the name and a button to access actions like deletion and renaming.
### File Search Page
#### Header:
- Similar to the home page.
#### Content:
- Search form for files and folders by name.
- List of found files. For each file, display the name and a button to navigate to the folder containing the file.
- Unauthenticated users are redirected to the authentication form.
- Controller for Specific File Access

## Other Considerations
### Sessions and Authentication
- Use Spring Security for authentication and access control.
- Use Spring Sessions, possibly with Redis, for managing sessions.
### SQL Database
- Use MySQL for storing user data.
- Integrate with Spring Security and Spring Data JPA for user management.
### File Storage (S3)
- Use MinIO, an S3-compatible storage service, for storing files.
- Structure file storage with buckets for each user, organizing files within each user's folder.
- Use AWS Java SDK or Minio Java SDK for file operations like upload, rename, and delete.
### Utilize Docker
- Utilize Docker for easy setup and management of required services like MySQL, MinIO, and Redis.
- Write a Docker Compose file to orchestrate the containers needed for the application stack.
## Login 
![Image alt](https://github.com/thelastofus3/Cloud-Storage/blob/master/cloud/login.png)

## Registration 
![Image alt](https://github.com/thelastofus3/Cloud-Storage/blob/master/cloud/registration.png)

## Home page
![Image alt](https://github.com/thelastofus3/Cloud-Storage/blob/master/cloud/main.png)
