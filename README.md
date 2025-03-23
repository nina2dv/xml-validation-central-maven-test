## Maven profiles to validate XML against XSD or Schematron.

XSD: `mvn clean verify -P xsd-validation`

Schematron: `mvn clean verify -P schematron-validation`

Both XSD and Schematron: `mvn clean verify -P all-validation`

---

XSD is more for structural validation. It describes what elements and attributes are allowed, their order,
how they can be nested, and data types like strings, numbers, dates.

Schematron is for business rules and contextual constraints.

---

To change the XML file for validation, either:

1. Change the default `<argument.xmlFile>` value in `pom.xml`.

2. Use the `-D` flag to override properties without changing the `pom.xml`:
```bash
# mvn <phase> -P <profile> -D<property>=<value> -D<property>=<value> ...

# Override xmlFile property to validate
mvn verify -P xsd-validation -Dargument.xmlFile=src/main/resources/xml/figure1b.xml

mvn verify -P schematron-validation -Dargument.xmlFile=src/main/resources/xml/figure1b.xml

mvn verify -P all-validation -Dargument.xmlFile=path/to/another_test.xml

# Could override the Schematron and XSD schema files too
# Ex: Override all three properties to use custom files
mvn verify -P all-validation \
    -Dargument.xsdFile=path/to/new_schema.xsd \
    -Dargument.schematronFile=path/to/new_validation.sch \
    -Dargument.xmlFile=path/to/new_test.xml
```

## JAR
```
java -jar xml-validation-X.X.X.jar ./src/main/resources/xsd/istar-rl-schema.xsd ./src/main/resources/schematron/istar-rl-schematron.sch ./src/main/resources/xml/figure1a.xml
```

## Pull Package
Add this to pom.xml:
```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<dependencies>
    <dependency>
        <groupId>io.github.nina2dv</groupId>
        <artifactId>xml-validation</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <mainClass>org.example.MyValidatorApp</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Add GitHub username and PAT in `settings.xml`:
```xml
<username>GITHUB_USERNAME</username>
<password>GITHUB_PERSONAL_ACCESS_TOKEN</password>
```

Run below:
```
mvn install -s settings.xml
```

Example java project:
```java
package org.example;
        
import com.example.validation.XsdValidator;
import com.example.validation.ValidationResult;

public class MyValidatorApp {
    public static void main(String[] args) {
        try {
            ValidationResult result = XsdValidator.validateXMLSchema("path/to/schema.xsd", "path/to/file.xml");
            if (result.isValid()) {
                System.out.println("XML is valid!");
            } else {
                System.err.println("XML is not valid: " + result.getErrors());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
```
```
mvn compile exec:java
```
---

The [xslt](https://github.com/nina2dv/xml-istar-rl/tree/main/src/main/resources/schematron/xslt) directory contains unzipped files from `schxslt-1.10.1-xslt-only.zip`
in [SchXslt releases](https://github.com/schxslt/schxslt/releases).

Validation using schematron in Java was inspired from this [video](https://www.youtube.com/watch?v=0OCULBADZr4&t=2s).