# URL_Shortening

## 0. 개요

- 모든 작업은 root 계정 기준으로 작업


## 1. JDK 1.8 설치 및 확인

1.1. java 및 javac 버전 확인

> 반드시 java와 javac 2가지 버전이 확인되어야 한다.<br>
2가지 버전이 확인이 될 경우 2번 항목으로 이동한다.
- [root@localhost ~]# java -version<br>
openjdk version "1.8.0_262"<br>
OpenJDK Runtime Environment (build 1.8.0_262-b10)<br>
OpenJDK 64-Bit Server VM (build 25.262-b10, mixed mode)
- [root@localhost ~]# javac<br>
bash: javac: 명령을 찾을 수 없습니다...<br>

1.2. 설치 가능한 java 확인

- [root@localhost Shortening]# yum list java*jdk-devel<br>
Loaded plugins: fastestmirror, langpacks<br>
Loading mirror speeds from cached hostfile<br>
 \* base: mirror.kakao.com<br>
 \* extras: mirror.kakao.com<br>
 \* updates: mirror.kakao.com<br>
Available Packages<br>
java-1.6.0-openjdk-devel.x86_64                                      1:1.6.0.41-1.13.13.1.el7_3                                      base   <br>
java-1.7.0-openjdk-devel.x86_64                                      1:1.7.0.261-2.6.22.2.el7_8                                      base   <br>
java-1.8.0-openjdk-devel.i686                                        1:1.8.0.322.b06-1.el7_9                                         updates<br>
java-1.8.0-openjdk-devel.x86_64                                      1:1.8.0.322.b06-1.el7_9                                         updates<br>
java-11-openjdk-devel.i686                                           1:11.0.14.0.9-1.el7_9                                           updates<br>
java-11-openjdk-devel.x86_64                                         1:11.0.14.0.9-1.el7_9                                           updates

1.3. java 설치
> 설치 완료 후 1.1. 항목 재 확인
- [root@localhost Shortening]# yum -y install java-1.8.0-openjdk-devel.x86_64<br>
Installed:<br>
  java-1.8.0-openjdk-devel.x86_64 1:1.8.0.322.b06-1.el7_9<br>
Complete!

## 2. Git 설치

2.1. Git 설치 확인
> git 의 버전이 정상 출력될 경우 2.3. 항목으로 이동한다.
- [root@localhost ~]# git --version<br>
bash: git: 명령을 찾을 수 없습니다...

2.2. Git 설치
> 설치 완료 후 2.1. 항목 재 확인
- [root@localhost ~]# yum -y install git<br>
Dependency Installed:<br>
  perl-Error.noarch 1:0.17020-2.el7           perl-Git.noarch 0:1.8.3.1-23.el7_8           perl-TermReadKey.x86_64 0:2.30-20.el7<br>
Complete!<br>

2.3. Git Repository 연결
- [root@localhost ~]# git clone https://github.com/DEV-JSW/URL_Shortening.git<br>
Cloning into 'URL_Shortening'...<br>
remote: Enumerating objects: 104, done.<br>
remote: Counting objects: 100% (104/104), done.<br>
remote: Compressing objects: 100% (73/73), done.<br>
remote: Total 104 (delta 15), reused 99 (delta 13), pack-reused 0<br>
Receiving objects: 100% (104/104), 2.93 MiB | 0 bytes/s, done.<br>
Resolving deltas: 100% (15/15), done.
- [root@localhost ~]# ll<br>
drwxr-xr-x. 4 root root   53  2월 16 11:40 URL_Shortening

## 3. Source Build

3.1. Build 기본 설정
- [root@localhost ~]# cd URL_Shortening/Shortening/
- [root@localhost Shortening]# ll<br>
합계 24<br>
-rw-r--r--. 1 root root 10070  2월 16 11:40 mvnw<br>
-rw-r--r--. 1 root root  6608  2월 16 11:40 mvnw.cmd<br>
-rw-r--r--. 1 root root  3788  2월 16 11:40 pom.xml<br>
drwxr-xr-x. 4 root root    30  2월 16 11:40 src<br>
- [root@localhost Shortening]# chmod 755 mvnw
- [root@localhost Shortening]# ll<br>
합계 24<br>
__-rwxr-xr-x.__ 1 root root 10070  2월 16 11:40 mvnw<br>
-rw-r--r--. 1 root root  6608  2월 16 11:40 mvnw.cmd<br>
-rw-r--r--. 1 root root  3788  2월 16 11:40 pom.xml<br>
drwxr-xr-x. 4 root root    30  2월 16 11:40 src

3.2. JDBC 설정
> 테스트용 DB User 필요.<br>테이블 생성 스크립트는 [Repository 에 업로드한 문서 참조.](https://github.com/DEV-JSW/URL_Shortening/raw/main/%ED%85%8C%EC%9D%B4%EB%B8%94%20%EB%AA%85%EC%84%B8%EC%84%9C.docx)
- [root@localhost Shortening]# vi src/main/resources/application.properties
    - 수정해야 할 JDBC 접속 정보는 아래와 같습니다.
    ```
    spring.datasource.url=jdbc:oracle:thin:@localhost:1521:orcl
    spring.datasource.username=coding
    spring.datasource.password=1234
    ```

3.3. Maven Clean & Install
- [root@localhost Shortening]# ./mvnw clean install<br>
[INFO] Scanning for projects...<br>
[INFO] --- maven-war-plugin:3.3.2:war (default-war) @ Shortening ---<br>
[INFO] Packaging webapp<br>
[INFO] Assembling webapp [Shortening] in [/root/URL_Shortening/Shortening/target/Shortening-0.0.1-SNAPSHOT]<br>
[INFO] Processing war project<br>
[INFO] Copying webapp resources [/root/URL_Shortening/Shortening/src/main/webapp]<br>
[INFO] Building war: /root/URL_Shortening/Shortening/target/Shortening-0.0.1-SNAPSHOT.war<br>
[INFO] <br>
[INFO] --- spring-boot-maven-plugin:2.6.2:repackage (repackage) @ Shortening ---<br>
[INFO] Replacing main artifact with repackaged archive<br>
[INFO] <br>
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ Shortening ---<br>
[INFO] Installing /root/URL_Shortening/Shortening/target/Shortening-0.0.1-SNAPSHOT.war to /root/.m2/repository/short/Shortening/0.0.1-SNAPSHOT/Shortening-0.0.1-SNAPSHOT.war<br>
[INFO] Installing /root/URL_Shortening/Shortening/pom.xml to /root/.m2/repository/short/Shortening/0.0.1-SNAPSHOT/Shortening-0.0.1-SNAPSHOT.pom<br>
[INFO] ------------------------------------------------------------------------<br>
[INFO] BUILD SUCCESS<br>
[INFO] ------------------------------------------------------------------------<br>
[INFO] Total time:  12.131 s<br>
[INFO] Finished at: 2022-02-16T11:59:20+09:00<br>
[INFO] ------------------------------------------------------------------------

3.3. Maven Package
- [root@localhost Shortening]# ./mvnw package<br>
[INFO] Scanning for projects...<br>
[INFO] --- maven-war-plugin:3.3.2:war (default-war) @ Shortening ---<br>
[INFO] Packaging webapp<br>
[INFO] Assembling webapp [Shortening] in [/root/URL_Shortening/Shortening/target/Shortening-0.0.1-SNAPSHOT]<br>
[INFO] Processing war project<br>
[INFO] Copying webapp resources [/root/URL_Shortening/Shortening/src/main/webapp]<br>
[INFO] Building war: /root/URL_Shortening/Shortening/target/Shortening-0.0.1-SNAPSHOT.war<br>
[INFO] <br>
[INFO] --- spring-boot-maven-plugin:2.6.2:repackage (repackage) @ Shortening ---<br>
[INFO] Replacing main artifact with repackaged archive<br>
[INFO] ------------------------------------------------------------------------<br>
[INFO] BUILD SUCCESS<br>
[INFO] ------------------------------------------------------------------------<br>
[INFO] Total time:  11.002 s<br>
[INFO] Finished at: 2022-02-16T11:59:39+09:00<br>
[INFO] ------------------------------------------------------------------------

## 4. 실행 환경 

4.1. 실행 기본 디렉토리 생성
- [root@localhost Shortening]# cd /root
- [root@localhost ~]# mkdir ShortService

4.2. 디렉토리 구조 생성
- [root@localhost ~]# cd ShortService/
- [root@localhost ShortService]# mkdir bin
- [root@localhost ShortService]# mkdir lib
- [root@localhost ShortService]# mkdir logs
- [root@localhost ShortService]# ll<br>
합계 0<br>
drwxr-xr-x. 2 root root 6  2월 16 12:12 bin<br>
drwxr-xr-x. 2 root root 6  2월 16 12:12 lib<br>
drwxr-xr-x. 2 root root 6  2월 16 12:12 logs

4.3. 실행 스크립트 생성
- [root@localhost ShortService]# cd bin/
- [root@localhost bin]# vi runner.sh
    - 실행 스크립트 내용
    ```
    #!/bin/sh
    
    APP_HOME="/root/ShortService"
    JAVA_HOME="/usr"
    
    APP_NAME="URL Shortening Service"
    PROCESS_ID="URL-Shortening-Service"
    
    XMS=512m
    XMX=512m
    
    APP_LOG_FILE=$APP_HOME/logs/urlShort.out
    
    EXISTS_SERVICE=`ps -ef | grep java | grep $PROCESS_ID`
    
    if [ "$1" = "start" ]; then
        if [ "$EXISTS_SERVICE" != "" ]; then
            echo "${APP_NAME} is already running.."
        else
            CUR_PATH=`pwd`
            cd $APP_HOME/bin
            nohup $JAVA_HOME/bin/java \
                -Dprocess.id=$PROCESS_ID \
                -Xms$XMS -Xmx$XMX \
                -XX:+PrintGCDetails \
                -jar $APP_HOME/lib/Shortening.war \
                1> $APP_LOG_FILE 2>&1 &
            sleep 2
            EXISTS_SERVICE=`ps -ef | grep java | grep $PROCESS_ID`
    
            PID=`echo $EXISTS_SERVICE | cut -f 2 -d " "`
    
            echo "${APP_NAME} (PID=${PID}) is started.."
    
            cd $CUR_PATH
    
            echo "Log File : ${APP_LOG_FILE}"
        fi
    elif [ "$1" = "stop" ]; then
        if [ "$EXISTS_SERVICE" != "" ]; then
            PID=`echo $EXISTS_SERVICE | cut -f 2 -d " "`
            kill ${PID}
            echo "${APP_NAME} (PID=${PID}) is killed."
        else
            echo "${APP_NAME} is not running."
        fi
    elif [ "$1" = "status" ]; then
        if [ "$EXISTS_SERVICE" != "" ]; then
            echo "${APP_NAME} is running.."
        else
            echo "${APP_NAME} is not running."
        fi
    else
        echo "Usage : $0 {start|stop|status}"
    fi
    ```
- [root@localhost bin]# chmod 755 runner.sh 
- [root@localhost bin]# ll<br>
합계 4<br>
**-rwxr-xr-x.** 1 root root 1365  2월 16 12:19 runner.sh

4.4. 실행파일 복사
- [root@localhost bin]# cd ../lib/
- [root@localhost lib]# cp /root/URL_Shortening/Shortening/target/Shortening-0.0.1-SNAPSHOT.war Shortening.war
- [root@localhost lib]# ll<br>
합계 49560<br>
-rw-r--r--. 1 root root 50745996  2월 16 12:20 Shortening.war

4.5. 서비스 실행 / 종료 / 상태 확인
- [root@localhost lib]# cd ../bin/
- [root@localhost bin]# 
    - 서비스 실행
        > 명령어 실행 후 PID 의 값이 비어있을 경우 서비스 실행 후 오류로 인한 종료.<br>
        Log File (/root/ShortService/logs/urlShort.out) 확인 필요.
        ```
        [root@localhost bin]# ./runner.sh start
        URL Shortening Service (PID=65516) is started..
        Log File : /root/ShortService/logs/urlShort.out
        ```
    - 서비스 종료
        - ./runner.sh stop
    - 서비스 상태 확인
        - ./runner.sh status
