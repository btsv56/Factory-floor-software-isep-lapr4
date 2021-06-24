#!/usr/bin/env bash
set -ev
cd Central\ System/eapli-base
mvn $1 dependency:copy-dependencies package
export BASE_CP=base.app.bootstrap/target/base.app.bootstrap-1.3.0-SNAPSHOT.jar:base.app.bootstrap/target/dependency/*;
java -cp $BASE_CP eapli.base.app.bootstrap.BaseBootstrap
