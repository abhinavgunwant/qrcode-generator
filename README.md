# qrcode-generator

A desktop GUI app to generate QR codes. Currently a fun project and a work-in-progress.

GitHub URL: https://github.com/abhinavgunwant/qrcode-generator

## Getting started
- Make sure you have Java (JRE) installed.
- Download JAR file for the latest version from the releases section in the
GitHub page.  
<i>If you're reading this README from the project home, the releases section 
should be towards right of the page.</i>
- Once downloaded, the JAR can be executed by double clicking (if this does not
work, try executing `java -jar <filename>`).
- After executing the jar, just type or paste the text you want to generate the
qr for. The qr can be saved as `.png` image by clicking save button under the
qr code preview.

## Development and Building

This is a maven project. To run the program, execute the following command:
```bash
mvn exec:java
```

To create an executable jar, just execute:
```bash
mvn package
```

## Creating installer

This project uses [jpackage](https://docs.oracle.com/en/java/javase/14/jpackage/packaging-overview.html#GUID-C1027043-587D-418D-8188-EF8F44A4C06A)
to create installers.

To create one yourself, build the project using the package command above, and
run the following commands based on the target operating system (**Note:**
These commands should also run on the target platform):

### Windows
```ps
jpackage --name qrcode-generator --input .\target --main-jar qrcode-generator-<version>-jar-with-dependencies.jar --app-version <version> --license-file .\LICENSE --win-shortcut --win-dir-chooser --icon .\src\main\resources\icon.ico --vendor "<your-name>"
```

### Linux
```bash
jpackage --name qrcode-generator --input ./target \
    --main-jar qrcode-generator-<version>-jar-with-dependencies.jar \
    --app-version <version> --license-file ./LICENSE \
    --icon .\src\main\resources\icon.ico --vendor "<your-name>"
```
