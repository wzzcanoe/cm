context manager
====

# cm
context manager  

# environment
jdk 1.8  
maven 3.6  
mysql 8 or 5  

# build with maven
git clone https://github.com/wzzcanoe/cm.git  
cd cm  
mvn clean package  
cd target  
[choose one of these three actions]  
java -jar cm.jar # if the packaging in pom.xml is jar  
java -jar cm.war # if the packaging in pom.xml is war  
throw the cm.war to tomcat # if the packaging in pom.xml is war  

# build with IDE
import pom.xml as an existing maven project  
select the project  
[choose one of these three actions]  
run as->java application  
run as->run on server  
run as->junit test  
