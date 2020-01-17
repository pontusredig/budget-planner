FROM openjdk:8-jre-alpine
COPY ./budgetplanner-0.0.1-SNAPSHOT.jar /usr/src/budget-planner/
WORKDIR /usr/src/budget-planner
EXPOSE 8080
CMD ["java", "-jar", "budgetplanner-0.0.1-SNAPSHOT.jar"]