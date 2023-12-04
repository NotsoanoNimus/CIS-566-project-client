# CIS-566-project-client
The client-side component of the Fall 2023 CIS-566 class group project.

This application is created and run cross-platform using Java, with [Maven](https://maven.apache.org/what-is-maven.html) as the build system and [JavaFX](https://openjfx.io/) as the GUI.


### Default Credentials
There are two users available for testing:
```text
admin@bookwarehouse.io   (password 'admin')    - Staff
someguy@bookwarehouse.io (password 'password') - Normal User
```


### Prerequisites
You **MUST** be running the [Silver Server](https://github.com/NotsoanoNimus/CIS-566-project-server) on _your local machine_ for any of this to work.
    
    If you do not use a local Silver Server, you WILL NOT be able to log in with the test users above!

Install the latest JDK for Java version 21 [here](https://www.oracle.com/java/technologies/downloads/#jdk21-windows).

If you would like to `compile and run` your own JAR file from the IDE, you will also need to [install JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/) (it's free to use for 30 days).


## Running
If all you want to do is run the output JAR file, make sure you have the JDK listed above and use [the compiled JAR file provided](https://raw.githubusercontent.com/NotsoanoNimus/CIS-566-project-client/master/silver-client.jar) with the `java -jar silver-client.jar` command.


## Compiling
To ease the JAR-file compilation process, the Maven dependency management framework is used to orchestrate building the Silver Client.

Follow these instructions to compile and run the JAR:

1. Open the Intellij IDEA application.
2. In the top-left context menu, click File > Open.
3. Navigate to this project's `SilverClient` folder.
  - You must specifically open the _SilverClient_ folder in IntelliJ. If you just open the outer GitHub repository folder containing this README file, it **will not work**.
4. Once the project is opened, indexed, and fully loaded, select the `Run Output JAR` build configuration from the top-right menu near the Play and Debug buttons.
5. Click the Play button to build and run the Silver Client.


### A Note About silver-server
In order to change the API target to your local machine (`localhost`) when running your own [silver-server](https://github.com/NotsoanoNimus/CIS-566-project-server) instance, or to some other `silver-server` host, you can do one of the following:

- If running the JAR file with `java.exe -jar NAME.jar`, set the `SILVER_TARGET_HOSTNAME` environment variable before calling the JAR.
- If running from within IntelliJ, click the dropdown for the `Run Output JAR` configuration and click Edit Configurations. In the "Run/Debug Configurations" window that pops up, you can modify the `Environment variables` which the program starts with. In here, add the `SILVER_TARGET_HOSTNAME` value.
- Manually go to the [appropriate entrypoint class](https://github.com/NotsoanoNimus/CIS-566-project-client/blob/master/SilverClient/src/main/java/xyz/xmit/silverclient/SilverLibraryApplication.java#L15) and swap the returned string value in the `getTargetSilverServerHostname` method with your own.
