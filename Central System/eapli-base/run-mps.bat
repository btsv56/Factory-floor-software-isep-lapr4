REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.mps.console\target\base.app.mps.console-1.3.0-SNAPSHOT.jar;base.app.mps.console\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% eapli.base.app.mps.console.BaseMPS