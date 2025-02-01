### Maven profiles to validate XML against XSD or Schematron.

XSD: `mvn clean verify -P xsd-validation`

Schematron: `mvn clean verify -P schematron-validation`

Both XSD and Schematron: ` mvn clean verify -P all-validation`

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

---

The [xslt](https://github.com/nina2dv/istar-rl-xml/blob/6528ac4c97a791826feb60a22776333fe91b3b7d/src/main/resources/schematron/xslt) directory contains unzipped files from `schxslt-1.10.1-xslt-only.zip`
in [SchXslt releases](https://github.com/schxslt/schxslt/releases).

Validation using schematron in Java was inspired from this [video](https://www.youtube.com/watch?v=0OCULBADZr4&t=2s).