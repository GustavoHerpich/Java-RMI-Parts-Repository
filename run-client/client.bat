@echo off
REM Muda para o diretório principal do projeto
cd /d %~dp0\..

REM Define a variável para o caminho dos binários (onde os .class compilados estão)
set BIN_PATH=build

REM Compila todos os arquivos .java
javac -d %BIN_PATH% src\br\com\parts\client\*.java src\br\com\parts\server\*.java src\br\com\parts\repository\*.java src\br\com\parts\repository\Impl\*.java src\br\com\parts\model\*.java src\br\com\parts\model\Impl\*.java src\br\com\parts\service\*.java src\br\com\parts\utils\*.java src\br\com\parts\connection\*.java src\br\com\parts\factory\*.java

REM Define o caminho da classe principal do cliente
set CLASS_CLIENT=br.com.parts.client.PartClient

REM Executa o cliente RMI
java -cp %BIN_PATH% %CLASS_CLIENT%

pause
