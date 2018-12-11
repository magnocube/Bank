SET mypath=%~dp0
echo %mypath:~0,-1%
java -jar bankrunnable.jar %mypath:~0,-1%