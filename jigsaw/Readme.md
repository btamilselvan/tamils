<p>
project name as module name. (com.success.one as project name and also a module name)
</p>

<p>
Unnamed modules -> non-modularized jars found in classpath. All packages will be automatically exported. The packages are visible only for other unnamed modules in classpath or automatic modules from module path. Not visible for named modules.
</p>

<p>
Automatic modules -> non-modularized jars found in the module path. can read packages from unnamed modules and also available for named modules.
</p>

<p>
A requires static clause expresses a dependency that is optional at run time.
</p>

<p>
split package -> a package cannot be exported from two modules.
</p>

<p>
the modules that implement a specific interface can export its implementation uses "provides"
</p>

<p>
the modules can use those implementations using "uses" and using ServiceLoader. All available implementations can be loaded using ServiceLoader. For e.g. four has one and three in module path and both one and three implements IOne. Both implementations will be available in four.
</p>

<p>
use "open" to allow access to private members using reflection
</p>

<p>
	"transitive" usage example:
	 4 -> 3 -> 1 .. in order for "four" to access packages from "one", either directly use "requires" in "four" or  make "three" transitively requires "one", so that when "four" imports "three", automatically "one" will be imported by "three".
	 
"transitive" -> A module can grant readability to additional modules, upon which it depends, to any module that depends upon it. Such "implied readability" is expressed by including the transitive modifier in a requires clause.

JDK example: java.sql package -> transitively requires java.logging. so any module that uses java.sql can access java.logging as well.
</p>

<p>
migration approaches: top-down, bottom-up
</p>

<p>
Maven modules are a way to organize your project into several subprojects (modules). With Maven, you can control the versions of these modules and the dependencies between these modules. Each module will produce an artifact.

Java modules are a way to strongly encapsulate your classes. It does not provide any means to control the version of an artifact you are using.
</p>

<p>
use javac like this ->  javac -d out --module-source-path "./*/src/main/java/"  $(find . -name "*.java")
This will compile both modules. for some reasons, javac complains "module not found in source path" error when full module path is used like this: javac -d out --module-source-path "./com.success.one/src/main/java/" --module com.success.one  $(find . -name "*.java")
</p>

## commands

	-p is synonym for --module-path -> points to root directory where modules are found
	-module-source-path -> points to modules root directory
	-module -> points to the module and the main class to run
	- jdeps - Java Dependency Analysis Tool, a command-line tool that processes .class files or the JARs that contain them, and analyzes the statically declared dependencies between classes
		jdeps --module-path out -R --module com.success.four
	- module name will be derived from module jar if "Automatic-Module-Name" is not present in manifest.mf. the dashes from the jar name will be replaced with dot. for e.g. com-success-four.jar will be resolved to com.success.four module
	- java -p out --module com.success.four/com.success.four.SimpleFour
	- java -p <modules root directory> --module <module-name>/<main class>
	- create executable jar with main class -> 
		jar -c --file jars/com-success-four.jar --main-class com.success.four.SimpleFour -C out/com.success.four .
	- run ->
		java -p jars --module com.success.four/com.success.four.SimpleFour
	- -p jars is where all modules jars are present.
	- create a standalone application using jlink (build a custom space-optimized JRE)
		jlink -p "jars;<path to jmods in jdk directory>" --add-modules com.success.three,com.success.three --output app-four
		jlink -p "jars;/c/Program Files/Java/jdk-11.0.5/jmods" --add-modules com.success.three,com.success.four --output app-four
	- run standalone app
		/bin/java --module com.success.four/com.success.four.SimpleFour
	- jmod is a file format that can store .class files and any other resource files or native libraries and anything that cannot be included in jar files
		jmod create --class-path jars/com-success-four.jar jmods/four.mod
		jmod extract --dir jmodout jmods/four.mod
	- multi jar is a new option to have a jar to include classes for multiple java versions
