## Library Book Management Service
This service exposes:
1. 5 APIs for CRUD operations based on ISBN
2. 1 API to search based on any given field
1. 1 API to upload a CSV file with books data

### Build instructions
To run the project, install Intellij community edition, 
import the project and run the LibraryManagementServiceApplication.java file.
This will start a web server on port 8080. Now, you can use Postman 
to submit requests for different APIs.

Alternatively, build and run the project using maven from command line.

Currently, unit-tests are only written for book controller and unit-tests for other classes are marked as ToDo.

## API details
1. GET localhost:8080/books/ : Gets all the books
2. POST localhost:8080/books/ : Add a book to the library where book details are passed as JSON object in form-data
3. GET localhost:8080/books/{ISBN} : Get book corresponding to given ISBN
4. PUT localhost:8080/books/{ISBN} : Update book corresponding to given ISBN with details provided as JSON object in form-data
5. DELETE localhost:8080/books/{ISBN} : Delete the book corresponding to given ISBN
6. GET localhost:8080/search?{params} : Search entire catalog based on the params passed
7. POST localhost:8080/upload : upload CSV file with book data where file is passed as multipart data
