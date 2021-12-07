# SonarQube-Plugin-for-Java

## Overview

This Java i18n SonarQube plugin is a Java code static analyzing plugin, it runs as a SonarQube platform plugin, the issue checking rule set of this plugin focuses on key internationalization issues, it covers internationalization issues around input/output, locales, formatting (dates, times, numbers, etc.), sorting, etc.

## Try it out

### Prerequisites

* JDK 11+ is installed.
* [Maven](https://maven.apache.org/install.html) is downloaded and setup correctly.
* Have a working [SonarQube](https://docs.sonarqube.org/latest/setup/get-started-2-minutes/) platform.

### Build & Run

1. In the project root folder, run:
```
mvn package
```
2. Copy the generated plugin artifact to the SonarQube plugin folder
```
cp ./target/java-i18n-rules-1.0-SNAPSHOT.jar <sonarqube-platform-folder>/extensions/plugins
```
Restart the SonarQube server to make this new plugin loaded.

3. In the Java project to be scanned, create a config file [sonar-project.properties](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/),
then run the command:
```
sonar-scanner
```

## Documentation

### Rule Set Information

The rule set of this plugin for internationalization static code analysis focus on key internationalization issues, particularly those issues that can cause more serious bugs in code. Plugin coverage for internationalization issues around input/output, locales, formatting (dates, times, numbers, etc.), sorting, etc.

- Rules V1002-1008 check for potential i18n issues with input/output.
- Rules V1010-1020 relate to locales. Many internationalization pieces, such as formatting, sorting, line-breaking, and more, depend on locales.
- Rules V1009-v1011 relate to date/time formatting issues.
- Rules V1014-V1018 relate to linguistic sorting/collation.

More details about the rules, the exact APIs checked, as well as suggested code fixes for resolution can be found in the HTML files that are part of this plugin.



## Contributing

The SonarQube-Plugin-for-Java project team welcomes contributions from the community. Before you start working with SonarQube-Plugin-for-Java, please
read our [Contributor License Agreement](https://cla.vmware.com/cla/1/preview). All contributions to this repository must be
signed as described on that page. Your signature certifies that you wrote the patch or have the right to pass it on
as an open-source patch. For more detailed information, refer to [CONTRIBUTING.md](CONTRIBUTING.md).

* The [github issues](https://github.com/vmware-samples/SonarQube-Plugin-for-Java/issues) in the repository are for bug reports and feature requests.

* Feel free to start a conversation with us at [Stackoverflow](https://stackoverflow.com/). Tag your question with "vmware-i18n-java-sonarqube-plugin" and we will get back to you.

## License

Licensed under the [GNU Lesser General Public License, Version 3.0](LICENSE)