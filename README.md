# Health IT Tools Statistics Logging

This project is a mini library to be used by hit tools to log usage statistics.

## Requirements
- Java 8+
- Maven

## Project Build
Clone this repository and run ```mvn clean install``` in the root directory

## Project Setup
To use this library you need to add these dependencies to your POM file if they are not already included

*NOTE : If you are using Spring Boot, these dependencies should already be included indirectly*

```xml
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.6.6</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.0.7</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-core</artifactId>
        <version>1.0.7</version>
    </dependency>
```

Then you should include the library dependency

*NOTE : this dependency is not available yet in NEXUS, to use please build and install locally*

```xml
  <dependency>
    <groupId>gov.nist.hit.hl7</groupId>
    <artifactId>hit-stats-logger</artifactId>
    <version>1.0.0</version>
  </dependency>
```

## Project Usage
If you are not already using logback in your project, create a **logback.xml** file in your **src/main/resources** folder.

Inside your **logback.xml** configuration file include hit-stats-logger configuration as follows and set properties.

```xml
<configuration>
    
  <include resource="gov/nist/hit/stats/logger/base.xml" />
  
  <!-- Set this property's value to the name of your tool (default: unspecified)-->
  <property name="HIT_STATS_TOOL_ID" value="...." />
  
  <!-- Set this property if you want log files to go to a specific directory (default: /var/log/hit-stats) -->
  <!-- In production, it is recommended that this variable be set by the admin as an environment variable in that case please do not override -->
  <property name="HIT_STATS_LOG_DIR" value="...." />
  
</configuration>
```

Once setup, you can use the library in your code as follows

```java
  import gov.nist.hit.logging.HITStatsLogger;

  /***
   * Create a log entry using username, organization, operation code, list of parameters
   * @param String username
   * @param String organization
   * @param String operation
   * @param List<String> parameterList
   */
  HITStatsLogger.log(username, organization, operation, parameterList)
  
  /***
   * Create a log entry using username, organization, operation code, list of parameters (spread)
   * @param String username
   * @param String organization
   * @param String operation
   * @param String[] parameters (spread)
   */
  HITStatsLogger.log(username, organization, operation, parameters)
    
  /***
   * Create a log entry using username, organization, operation code, single parameter
   * @param String username
   * @param String organization
   * @param String operation
   * @param String parameter
   */
  HITStatsLogger.log(username, organization, operation, parameter)
  
  /***
   * Create a log entry using username, organization, operation code
   * @param String username
   * @param String organization
   * @param String operation
   */
   HITStatsLogger.log(username, organization, operation)
   
  /***
   * Create a log entry using operation code, parameters (spread)
   * @param String username
   * @param String organization
   * @param String operation
   */
   HITStatsLogger.logOp(organization, parameters)
   
  /***
   * Create a log entry using operation code, parameters (spread)
   * @param String operation
   * @param String[] parameters (spread)
   */
  HITStatsLogger.logOp(operation, parameters)

  /***
   * Create a log entry using operation code
   * @param String operation
   */
  HITStatsLogger.logOp(operation)

```

## Log file format

The log file is in a TSV (tab separated values) format
Special characters have been escaped as follows :
```
   \n for newline,
   \t for tab,
   \r for carriage return,
   \\ for backslash.
```
Value Labels are the following
```
DATE_TIME    USERNAME    ORGANIZATION    OPERATION    [PARAMETER]*
```

The DATE_TIME format is the following : YYYY-MM-DD HH:mm:ss.SSS

## Log directory hierarchy
```
.
└── HIT_STATS_LOG_DIR
      └── VERSION
            └── HIT_STATS_TOOL_ID
                  └── hit-v${VERSION}.${HIT_STATS_TOOL_ID}.%d{yyyy-MM}.log
      
```




