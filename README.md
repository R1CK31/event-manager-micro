# event-manager-micro
# Overview
This project is an Event Management system made up of separate Spring Boot services that talk to each other over REST and use Eureka for service discovery. Instead of one large, all-in-one application, each service, such as users, events, categories, venues, tickets, payments, and notifications, runs independently with its own data and logic. At startup, every service registers itself with Eureka, so when the Event Service needs to talk to the User or Notification service, it looks up the service address in Eureka instead of using hard-coded URLs.

