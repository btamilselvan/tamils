
1. project name as modudle name. (com.success.one as project name and also a module name)
2. use javac like this ->  javac -d out --module-source-path "./*/src/main/java/"  $(find . -name "*.java")
This will compile both modules. for some reasons, javac complains "module not found in source path" error when full module path is used like this: javac -d out --module-source-path "./com.success.one/src/main/java/" --module com.success.one  $(find . -name "*.java")

> Unnamed modules -> non-modularized jars found in classpath. All packages will be automatically exported. The packages are visible only for other unnamed modules in classpath or automatic modules from module path. Not visible for named modules.
> Automatic modules -> non-modularized jars found in the module path. can read packages from unnamed modules and also available for named modules.
> split package -> a package cannot be exported from two modules.
> the modules that implement a specific interface can export it s  

