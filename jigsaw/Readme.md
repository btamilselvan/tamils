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
use javac like this ->  javac -d out --module-source-path "./*/src/main/java/"  $(find . -name "*.java")
This will compile both modules. for some reasons, javac complains "module not found in source path" error when full module path is used like this: javac -d out --module-source-path "./com.success.one/src/main/java/" --module com.success.one  $(find . -name "*.java")
</p>

## commands
<p>
	-p is synonym for --module-path
	jdeps - Java Dependency Analysis Tool, a command-line tool that processes .class files or the JARs that contain them, and analyzes the statically declared dependencies between classes
</p>
