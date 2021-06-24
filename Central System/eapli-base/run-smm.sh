#!/bin/bash
BASE_CP=base.app.smm.console\target\base.app.smm.console-1.3.0-SNAPSHOT.jar;base.app.smm.console\target\dependency\*;

java -cp %BASE_CP% eapli.base.app.smm.console.BaseSMM
