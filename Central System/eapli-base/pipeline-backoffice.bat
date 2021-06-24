ECHO OFF
set project_dir=%CD%
call delete-db.bat
cd /d %project_dir%
call rebuild-all.bat %1
call run-bootstrap.bat
call run-backoffice.bat
