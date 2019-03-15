Discovery service:
	Keeps tracks of all available services, does load balancing.
	@EnableEurekaServer
	When using config server, make sure to use bootstrap.yml instead of default applicaiton.properties (it didn't work with app.properties)

Gateway service:
	All requests go thru gateway and gateway with the help of discovery distributes the load to the available services. (round-robin). 
	Make sure *not* to extend SpringBootServletInitializer and make sure spring-web dependency is not there in pom.xml.
	
Person and address services:
	Are the actual services and register with discovery service.
	
Config service:
	To maintain the configuration in one place.
	The config files should be in git repo for prod deployments. But for development this can be stored in the local file storage. But if local file storage is used then 
	the native profile has to be activated.  
	
Communication b/w services:
	Use Feign clients. Make sure to enable Feign clients in the application class.
	
Include maven build plugin and package code using mvn package command. (this will create an executable jar)
Start spring boot using java command. port can be dynamically changed using vm arguments.
<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<version>2.1.3.RELEASE</version>
				<executions>
					<execution>
						<goals>
							<goal>repackage</goal>
						</goals>
					</execution>
				</executions>
			</plugin>

mvn package

If you do not include the <execution/> configuration, as shown in the prior example, you can run the plugin on its own (but only if the package goal is used as well), as shown in the following example:
mvn package spring-boot:repackage

java -jar -Dserver.port=80823 abc.jar
