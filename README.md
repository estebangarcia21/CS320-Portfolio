# CS 320 Portfolio

Esteban Garcia  
Southern New Hampshire University  
Software Test, Automation, and Quality Assurance

This repository contains my contact service from Project One and my summary and reflections report from Project Two. The contact service uses Java and JUnit to test adding, updating, and deleting contacts. It stores contacts in memory and does not use a database or user interface.

## Project artifacts

- [Contact.java](ContactService/Contact.java)
- [ContactService.java](ContactService/ContactService.java)
- [ContactTest.java](ContactService/ContactTest.java)
- [ContactServiceTest.java](ContactService/ContactServiceTest.java)
- [Project Two: Summary and Reflections Report](ProjectTwo/CS320_Project_Two_Report.docx)

## How can I ensure that my code, program, or software is functional and secure?

I start by checking my code against the requirements and writing tests for both valid and invalid inputs. For the contact service, that meant testing null values, field length limits, phone numbers, duplicate IDs, and updates that should fail. I also checked that a rejected update leaves the original contact information alone. Running those tests after changes helps me catch problems before they affect something else. Security takes more than passing unit tests, though. For an application that handles real user data, I would also review access controls, protect sensitive information, keep dependencies updated, and test how the different parts of the application work together. This project helped me see why input validation matters, but I would not treat it as proof that a complete application is secure.

## How do I interpret user needs and incorporate them into a program?

I break the requirements into specific behaviors that I can implement and test. For example, the requirement that a phone number contain exactly 10 digits became tests for valid numbers, numbers that were too short or too long, and input containing letters. The requirement for a unique contact ID also meant checking that a duplicate could not replace an existing contact. Looking at the requirements this way helps me avoid making assumptions about what the user wants. If something is unclear, I would ask for an example or clarification before building around my own interpretation.

## How do I approach designing software?

I try to keep the design simple and give each class a clear responsibility. In this project, Contact handles the contact's data and validation, while ContactService handles storing contacts and finding them by ID. Keeping those responsibilities separate made it easier to test problems in one place without changing everything else. I also reused validation methods so creating a contact and updating one follow the same rules. My approach is to build a small working version, test it against the requirements, and improve it as I find gaps. I want testing to be part of the design process instead of something I leave until the end.

## Running the tests

Use JDK 21 and Maven, then run this command from the repository folder:

```sh
mvn clean verify
```

The contact portion has 18 passing JUnit tests, with no failures, errors, or skipped tests. JaCoCo measured 100% line, branch, and instruction coverage for both Contact and ContactService. The build checks that each class stays at or above 80% coverage. The HTML coverage report is generated at `target/site/jacoco/index.html`.

The Project Two report discusses the earlier 40-test run for the full project, which also included tasks and appointments. This repository contains the later contact-service tests only. Coverage is useful feedback, but I still need to check that the assertions test the behavior the requirements actually call for.
