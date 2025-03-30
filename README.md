Overview
------------------------------------------------------------------------------------------------------------------------------------------------
The ExpensesTracker App is a Kotlin-based mobile application that allows users to track their expenses efficiently. The app enables users to add, delete, or edit expenses with associated details such as category and price. Users can also sort their expenses by title, date, category, and price in both ascending and descending order.

This app was built using MVVM Clean Architecture to ensure that the app is scalable, maintainable, and testable. The Clean Architecture was the core reason for creating this app, as it facilitates separation of concerns, testability, and flexibility to make future improvements. This is not focused too much on UI/UX.

Features
------------------------------------------------------------------------------------------------------------------------------------------------
Add Expense: Users can add a new expense, specifying the title, category, price, and date.

Edit Expense: Modify existing expenses by updating any of their details.

Delete Expense: Remove any expense from the list.

Sort Expenses: Sort expenses by various parameters:

Title (ascending or descending)

Date (ascending or descending)

Category (ascending or descending)

Price (ascending or descending)

Each category has a unique color for a better represantation.

Technologies Used
------------------------------------------------------------------------------------------------------------------------------------------------
Kotlin

Hilt: Used for dependency injection, ensuring that components such as ViewModels, UseCases, and repositories are injected properly.

Room Database: Utilized for local storage to persist expense data in a structured and efficient way.

MVVM Clean Architecture:

Model-View-ViewModel (MVVM): To separate UI logic from business logic, promoting cleaner code and easier testing.

Clean Architecture: To maintain a clear separation of concerns between different layers (presentation, domain, and data).

Use Cases: Encapsulates business logic and makes it reusable in the ViewModel layer.

Flow: To manage asynchronous data and make the app reactive by leveraging Kotlin Flow for stream-based data handling.

Sealed Classes: Used to represent different types of states for better handling of UI states.

Layers:
------------------------------------------------------------------------------------------------------------------------------------------------

Activities are responsible for interacting with the ViewModel.

The ViewModel manages UI-related data and provides the data to the UI in a lifecycle-conscious way.

Domain Layer: This layer contains business logic and use cases that represent actions users can perform with the data.

Use Cases: Encapsulate the business logic (such as adding, editing, deleting, and sorting expenses).

Data Layer: This layer is responsible for data management and provides access to the data from various sources.

Room Database: Manages data persistence.

Repositories: Fetch and update data from the local database and provide it to the domain layer.

Data Flow:
The app uses Kotlin Flow to manage asynchronous tasks like fetching or updating data.

Sealed classes are used to represent different UI states, which helps in managing different UI conditions.

Business logic is encapsulated in Use Cases, so the app can easily be extended without affecting the rest of the app.

UI-related code in the ViewModel is kept separate from the business logic, making it easier to test the app's core functionality independently of the UI.

The app can be easily extended in the future to support new features without introducing tight coupling between components.

<img width="322" alt="image" src="https://github.com/user-attachments/assets/518af1e5-b6e3-451b-a503-d2c1347289bc" />
<img width="313" alt="image" src="https://github.com/user-attachments/assets/74e0484a-18bb-4f70-9cf2-67ea0b09fdd9" />

