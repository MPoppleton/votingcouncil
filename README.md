# Voting Council App _v1.0.0_
Designed for _WildFly 8.2.0_ 
Also tested on Jboss _7.1.1_

## Description
A simple application that allows users to create questions and vote on them.

## Instructions
Move the datasource file (src/main/webapp/WEB-INF/votingapp-ds.xml), uncomment the datasource (Remove the <!-- and --> before and after the <datasource> tags) and place it in your deployments folder (jboss or wildfly standalone/deployments) or use your own datasource and edit the persistence.xml within the app before you build it.

Ensure that your datasoure deploys correctly! If it doesnt, check the logs and fix 'er up.

Build (mvn clean install in the votingcouncil/ directory) the project and deploy the .war file (found in votingcouncil/target/) in JBoss/WildFly (standalone/deployments/). Once it has deployed, go to your web context hosted (default is localhost:8080/votingapp) and try it out! 

See _instructions.txt_ for further help on how to configure your datasource or persistence adapter.
