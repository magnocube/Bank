SET mypath=%~dp0
echo %mypath:~0,-1%
start javaw -jar bankrunnable.jar %mypath:~0,-1%