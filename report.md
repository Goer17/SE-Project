# Group Report

## 1. The Purpose and Scope of the Application

The purpose of this application is to provide a virtual banking system tailored for children, enabling them to learn financial management skills in an engaging and interactive way. This system is designed to help children understand the value of money, manage their savings, and achieve financial goals through practical experiences.

### Users

**Primary Users**

**Children (Aged 5-15):** The main users who will interact with the application to manage their virtual accounts, complete tasks to earn money, set savings goals, and track their financial progress.

**Parents/Guardians:** Users who will create and manage accounts for their children, set tasks, oversee transactions, and guide their children in financial decision-making.

### Main Features

**Account Creation**

User name and password (at least six digits) are required to create a user.

User name admin and password 123456 for the administrator interface

**Balance Tracking**

Display the current balance of each account.

**Deposits and Withdrawals**

Enable children to deposit virtual money earned from tasks.

Enable children to withdraw virtual money, with restrictions to prevent negative balances.

**Task Setting**

Allow parents to set tasks or activities (e.g., chores, exercises) that reward children with virtual money upon completion.

**Transactions**

Provide a transaction history to help children understand their financial activities.

**Savings Goals**

Allow children to feel a sense of accomplishment and satisfaction while making deposits.

**Leaderboard**

Displays the ranking of savings amounts among users so that parents can visualise the overall picture.(Visible only on the parent's end)

## 2. Project Management

Effective project management is essential for the successful development and deployment of the application. The following techniques and tools are employed:

### Techniques and Tools

**Agile Methodology**

Use iterative development cycles (sprints) to continuously improve and adapt the application based on user feedback and changing requirements.

**Project Management Tools**

Use tools like JIRA or Trello for task tracking, sprint planning, and progress monitoring.

**Version Control**

Use Git for version control to manage code changes and collaborate efficiently within the development team.

**Code Storage**

Using GitHub for code storage, collaboration, and code review. 

**Communication Tools**

Utilize Zoom, Microsoft Teams, Lark for team communication and coordination.

### Planning and Estimation

**Sprint Planning**

Break down the project into manageable sprints, each focusing on delivering specific features or improvements.

**Estimation Techniques**

Use techniques like Planning Poker or T-shirt sizing to estimate the effort required for each task.

### Decision Making

**Collaborative Decision Making**

Encourage input from all team members and stakeholders to make informed decisions.

**Regular Meetings**

Conduct daily stand-ups, sprint reviews, and retrospectives to ensure alignment and address any issues promptly.

### Adapting to Changes

**Flexible Planning**

Be prepared to adjust plans based on user feedback and changing requirements.

**Continuous Integration and Deployment**

Implement CI/CD practices to enable frequent and reliable updates to the application.

## 3. Requirements

To ensure that the application meets the needs of its users, the following fact-finding techniques, iteration, prioritization, and estimation methods are employed:

### Fact-Finding Techniques

**Interviews and Surveys**

Conduct interviews and surveys with potential users (children and parents) to gather detailed requirements and preferences.

**Workshops**

Organize workshops with stakeholders to discuss and refine the application’s features and functionalities.

**Observation**

Observe user interactions with early prototypes to identify usability issues and areas for improvement.

### Iteration

**Prototyping**

Develop prototypes and conduct user testing to gather feedback and make iterative improvements.

**Incremental Development**

Implement features in small increments, allowing for frequent testing and validation with users.

### Prioritization

**MoSCoW Method**

Use the MoSCoW method (Must have, Should have, Could have, Won’t have) to prioritize features based on their importance and impact.

**User Stories**

Prioritize user stories based on the value they deliver to the users and the overall project goals.

### Estimation

**Effort Estimation**

Estimate the effort required for each task using story points or hours, based on team capacity and complexity.

**Velocity Tracking**

Track the team’s velocity to forecast the completion of future tasks and adjust plans accordingly.

By using these techniques and methods, the project team aims to deliver a high-quality application that meets user needs and adapts to changes effectively.

## 4.Analysis and Design

### UML Diagram

<img src="https://typora-1313035735.cos.ap-nanjing.myqcloud.com/img/2024-05-24-121502.png" style="zoom:80%;" />

### Architecture

The project follows a client-server architecture with a clear separation between the front-end user interface and the back-end data management. The primary components include:

**GUI Components**: Represented by various Swing pages (`HomePage`, `LoginPage`, `RegisterPage`, etc.) that handle user interaction.

**Data Management**: Managed by the `XMLDBManager` class which interacts with XML files to perform CRUD operations.

**Session Management**: Handled by the SessionManager class which keeps track of the current user session.

### Classes

The project is composed of several key classes, each with specific responsibilities:

**`User`**: Represents a user in the system with attributes like name, email, and password.

**`Transaction`**: Represents financial transactions with attributes like type, date, and amount.

**`FixedDeposit`**: Represents fixed deposit accounts with attributes like interest rate and maturity date.

**`SessionManager`**: Manages user sessions and tracks the current user.

**`XMLDBManager`**: Handles all data interactions, specifically with XML files for storing and retrieving user and transaction information.

### Components

The project is modular, with distinct components for different functionalities:

**`GUI Components`**: Each page is a separate component managing its own UI elements and user interactions.

**`Data Management Component`**: The `XMLDBManager` acts as the data layer, providing methods for data access and manipulation.

**`Session Management Component`**: The `SessionManager` tracks and maintains user session information, providing methods to set and get the current user.

### Design Principles

The design follows several key principles:

**Separation of Concerns**: Different classes handle different aspects of the application, such as UI, data management, and session management, ensuring a clear separation of concerns.

**Encapsulation**: Each class encapsulates its data and behavior, providing public methods for interaction while keeping the internal state hidden.

**Modularity**: The system is designed in a modular fashion, with each component (UI pages, data manager, session manager) functioning independently.

**Reusability**: Components like `CustomTextField`, `CustomLabel`, and `CustomPasswordField` are designed to be reusable across different parts of the UI.

## 5.Implementation

Discuss the implementation strategy and iteration/built plan in this section.

### Implementation Strategy

The implementation strategy can be broken down into several key phases, focusing on building the backend first and then moving on to the frontend. Here's a step-by-step breakdown:

**Backend Development**:

**Phase 1: Core Data Classes**:

Implement the core data classes like `User`, `Transaction`, and `FixedDeposit`.

Define their attributes and methods to handle data encapsulation and manipulation.

**Phase 2: Data Management**:

Develop the `XMLDBManager` class to handle all CRUD operations with XML files.

Implement methods for adding, deleting, updating, and retrieving users and transactions.

Ensure robust error handling and validation within these methods.

**Phase 3: Session Management**:

Implement the `SessionManager` class to manage the current user session.

Develop methods to set and get the current user, ensuring secure session handling.

**Frontend Development**:

**Phase 4: Common UI Components**:

Develop reusable UI components such as `CustomTextField`, `CustomLabel`, `CustomPasswordField`, and `Button`.

Ensure these components are flexible and can be styled easily.

**Phase 5: Individual Pages**:

Implement each page one by one starting with `LogPage` and `RegisterPage` for user authentication.

Follow with `HomePage`, `ProfilePage`, `AdminPage`, and `FixedDepositCatalog`.

Ensure each page interacts properly with the backend via the `SessionManager` and `XMLDBManager`.

**Phase 6: Board Forms and Page Navigation**:

Develop the `BoardForm` and other forms that might be used for displaying data or collecting user inputs.

Implement page navigation to ensure a smooth user experience as users move between different parts of the application.

### Iteration/Build Plan

**Iteration 1: Basic Backend Setup**:

Complete the implementation of core data classes (`User`, `Transaction`, `FixedDeposit`).

Implement the `XMLDBManager` class with basic CRUD operations for `User`.

Set up basic error handling and validation in the backend.

**Iteration 2: User Authentication**:

Implement the `SessionManager` class.

Develop `LogPage` and `RegisterPage` to allow user login and registration.

Integrate these pages with the backend to handle user data and sessions.

**Iteration 3: Core User Interface**:

Implement the `HomePage` and `ProfilePage`.

Ensure these pages can display user-specific data by interacting with `SessionManager` and `XMLDBManager`.

Develop and integrate common UI components to ensure a consistent look and feel.

**Iteration 4: Transaction Management**:

Extend `XMLDBManager` to handle `Transaction` CRUD operations.

Implement transaction-related UI components and integrate them into relevant pages.

Ensure transactions can be added, viewed, and managed by the user.

**Iteration 5: Admin Features**:

Implement the `AdminPage` to allow administrative functions such as user management.

Ensure admin functionalities interact properly with the backend.

**Iteration 6: Final Touches and Testing**:

Perform comprehensive testing to ensure all components work seamlessly.

Fix any bugs and optimize performance.

Refine the UI/UX for better usability and accessibility.

## 6.Testing

### Test Strategy and Test Technique

In the development process of this children's virtual bank project, we adopted a comprehensive testing strategy to ensure functionality testing from all aspects of the system requirements, thus ensuring the overall functional integrity of the developed software.

During development, we adopted Test-Driven Development (TDD) for some parts of the project to ensure the completeness of features and simplify the debugging process. This effectively supported rapid iteration and continuous improvement, achieving relatively good results.

### Unit Testing

In this part of our testing strategy, we mainly tested the core code of our banking system. In this section, we focused on testing the smallest units of code, such as specific methods, functions, and classes, to ensure they work as expected according to the requirements.

In this section, we primarily used white-box testing techniques to ensure that the internal logic of the programs was correct and that all code statements were thoroughly tested.

### Integration Testing

In this part of our testing strategy, we conducted joint tests of multiple components to ensure that independently developed software modules could interact normally when working together. This included some tests on database management and some tests on user interactions, among others.

In this section, we primarily used black-box testing techniques to check if the system's functionalities met our expectations.

### System Testing

In this part of our testing strategy, we attempted to simulate a real operational environment with virtual users to verify the behavior of the entire software as a complete system.

In this section, we mainly used scenario-based testing, a validation testing technique, to check if the system's basic requirements could be realized in practical scenarios and meet the experience expectations of target users.

### Test Results

During the testing process, we identified some actual issues in our developed banking system, including problems with underlying logic and GUI issues. For example, during transaction testing, we identified and fixed a calculation error with transaction amounts. Additionally, we corrected issues related to invalid numeric entries. In system testing and scenario-based testing, we identified some defects in the GUI experience for users and made some modifications.

Overall, the testing phase played a significant role in our development process, contributing markedly to our development progress and project management. During the testing stage, we identified many problems with the software and made corrections, ensuring that our product could meet user experience expectations.

## Future iterations

The following features will be added in the future to enrich the software for a better user experience.

**Customizable Savings Goals:**

Parents have the ability to customize savings goals for each child, tailoring them to individual needs and preferences.

Children can set their own savings goals within the parameters defined by their parents, fostering a sense of ownership and responsibility.

**Visualization of Children's Savings:**

The application will provide visual representations of children's savings through interactive line graphs, allowing both parents and children to track progress over time.

Graphs will display savings trends, milestones achieved, and remaining targets, enhancing financial literacy and goal-setting skills.

**Support for Multiple Currency Systems with Exchange Rates:**

The application will support multiple currency systems, including RMB, Euro, USD, and others, with real-time exchange rate updates.

Transactions involving different currencies will be automatically converted based on prevailing exchange rates, ensuring accurate financial management across diverse currency types.

**Individualized Rewards Based on Ranking:**

Children will be able to view their own ranking within the user community, with different rankings offering corresponding rewards or incentives.

Achieving higher rankings may unlock special bonuses, privileges, or recognition within the application, motivating children to save and manage their finances effectively.

**Educational Content and Insights:**

The application will curate and push educational articles and information related to financial literacy, tailored to the age and interests of children.

Insights and analysis tools will provide parents with actionable recommendations based on their children's savings habits and financial behavior, fostering informed decision-making and healthy financial habits from an early age.

By incorporating these additional features into the application, we aim to provide a comprehensive and engaging platform that promotes financial education and responsibility among children while offering valuable insights and tools for parents to support their children's financial journey.

**Enhanced Security Measures:**

Currently, passwords are stored in XML format within the application's database. However, as part of future iterations, the project team will implement a more secure approach by storing passwords as MD5 hash values.

Storing passwords as MD5 hash values enhances security by converting them into irreversible strings of characters, reducing the risk of unauthorized access to user accounts in the event of a data breach.
 This implementation aligns with industry best practices for password storage and strengthens the overall security posture of the application, ensuring the confidentiality and integrity of user credentials.

By incorporating these additional features into the application, we aim to provide a comprehensive and engaging platform that promotes financial education and responsibility among children while offering valuable insights and tools for parents to support their children's financial journey.

**Transfer Functionality:**

The application will include a transfer functionality, allowing children to transfer virtual money to other users within the system.

Transfers will be logged and tracked, providing a detailed history of all transactions to both parents and children.

This feature will enable children to practice peer-to-peer transactions, promoting a deeper understanding of financial transactions and responsibility.

Parents will be notified of any transfers, ensuring they can monitor and guide their children's financial activities.

By including these enhancements, the application will provide a robust platform for financial education, secure user management, and comprehensive financial tracking for both parents and children.