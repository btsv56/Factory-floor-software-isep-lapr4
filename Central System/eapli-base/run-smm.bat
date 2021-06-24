REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.smm.console\target\base.app.smm.console-1.3.0-SNAPSHOT.jar;base.app.smm.console\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% eapli.base.app.smm.console.BaseSMM