ECHO OFF
set -ev
set project_dir=%CD%
call delete-db.bat
cd /d %project_dir%
call build-all.bat %1
call run-bootstrap.bat
