# Voting Council App _v1.0.0_
Designed for _JBoss 7.1.1_ 

## Description
A simple application that allows users to create questions and vote on them.

## Instructions
Create a tribalcouncil schema in your MySQL DB
Just run the tribal.sql script under _src/main/db/_ to create required tables,
and update your hibernate config respectively.

Or let hibernate do it's magic! 

Build and deploy the .war file and go to your jboss web context hosted (/votingapp) 
and try it out! 

See _instructions.txt_ for further help on how to configure your jboss datasource persistence adapter, or 
simply change the persistence adapter used in the persistence.xml file to match your currently configured one.

