# CarInfoApp: General information about cars

The application provides general information about the most popular cars.

When you launch the application on the first screen, you can see a list of the most popular car manufacturers.
Then, when you select a specific manufacturer, the next screen displays information with a list of models from that manufacturer.
Then, when you select a specific model, the next screen will display information with a list the model build years of that model.
And finally, the detail screen displays a summary of the specific model and year of manufacture.
At any time, you can return to the previous screen and request data for another manufacturer or model.

## Getting started
Before the first build of the project, you need to configure the settings for accessing the server API:
- in class CarModule set CARS_BASE_URL value as "https://api-aws-eu-qa-1.auto1-test.com/v1/car-types/"
- in class CarApi set API_KEY value as "coding-puzzle-client-449cc9d"
Another additional actions to refine the program code are not required.

## Features
The application contains several important features:

### Paging
The list of manufacturers supports pagination of 15 records when loading data from the server

### Decoration
Each element of the list in all screens is displayed with a custom background depending on the position in the list, even or odd.
The most popular car models are displayed with their logos stored locally.

### Context search
The list of models supports contextual search in toolbar with animated display of changes.

### Change orientation support
All screens support configuration changes, such as device rotation and mode change between portrait and landscape.
At the same time, the car models screen supports saving search results when changing the configuration, 
and the detail screen supports different layouts for portrait and landscape modes.

### Smooth animated display
The detail screen contains an animated data area at the bottom that fades in and out as you open and close the screen, respectively.

### Caching
The application supports caching of all network requests and allows you to avoid excessive load on the server
and minimize the user's waiting time for the result.

### Release version
The application fully supports work in the release version with code obfuscation and is ready for signing and publishing

## Architecture and project structure
The application uses a modern approach to architecture using principles Clean Architecture and SOLID for layering all components
and the relationship between them.

### Data layer
Package "data". Contains classes for making requests to the network and a repository implementation for transforming data into suitable models
for domain layer.

### Domain layer
Package "domain". Contains all business logic with a set of use cases to perform all the necessary tasks for the subsequent display
of information to the user.

### Presentation layer
Package "presentation". Contains all classes for displaying information on screens and provides processing and transmission of user-entered data.
For interaction between the components of the layer, a standard approach is used with Architecture Components and the MVVM pattern.

### DI
Package "di". Provides a dependency injection implementation to avoid manually creating instances of classes supplied as dependencies
and controlling the number of such instances that are created.

### Testing
Package "test". All implementations of data and domain layer classes, as well as some classes from the presentation layer (ViewModel classes),
are completely covered by unit tests.

## 3d-party libraries
The application uses several 3d-party libraries for it work:
- [Retrofit](https://github.com/square/retrofit) - to make requests to the network
- [RxJava](https://github.com/ReactiveX/RxJava) - to perform asynchronous tasks
- [Dagger](https://github.com/google/dagger) - framework to implement dependency injection
- [Mockk](https://mockk.io/) - for support testing on Kotlin

## Contacts
For all questions related to improving the project, fixing bugs, etc., do not hesitate to contact the author of the project
Aleksandr Leliukh (lelyuh46@gmail.com)

Enjoy work with project and don't break it! :)

