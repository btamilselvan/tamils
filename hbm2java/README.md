#### Notes and References

- The hibernate-tools-maven plugin has three goals and we are using `hbm2java` to generate entities from database.
- Populate database connection information in `hibernate.properties`.
- Use `hibernate.revenge.xml` for type mapping.
- Update entity package name in pom.xml.
- Use pojo templates (.ftl files) to change the generated entity settings. 
- Run `mvn clean generate-sources` to generate entities.
- Checkout <a href="https://github.com/hibernate/hibernate-tools/blob/main/maven/README.md"> this link </a> for more information.