pipeline {
    agent any
    stages {
        stage('install dependencies') {
            steps {
                	sh 'sudo apt-get install maven -y'
		        sh 'mvn --version'
		    	sh 'sudo DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server-5.7'
		    	sh 'sudo mysql -uroot -proot -e "create database if not exists movie"'
            }
        }
        stage('build maven') {
            steps {
                dir("rating") {
                    sh 'pwd'
                    sh 'mvn clean install'
                    sh 'mvn install package'
                }
            }
        }
    }
    post {
        success{
		    archiveArtifacts artifacts: 'rating/target/rating-0.0.1-SNAPSHOT.jar'
		    sh '''
		        JENKINS_NODE_COOKIE=dontKillMe scp -r /var/lib/jenkins/workspace/rating-build-test-deploy/rating/target/rating-0.0.1-SNAPSHOT.jar ubuntu@149.165.168.227:/home/ubuntu/Spark/
			nohup ssh -f ubuntu@149.165.168.227 '
			sudo apt update
			sudo apt install default-jdk -y
			sudo DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server-5.7
			sudo mysql -uroot -proot -e "create database if not exists movie; ALTER USER "root"@"localhost" IDENTIFIED WITH mysql_native_password BY "root";"
			rm -rf /home/ubuntu/Spark/
			mkdir -p /home/ubuntu/Spark/
			killall -9 java
			java -jar /home/ubuntu/Spark/rating-0.0.1-SNAPSHOT.jar'
		    '''
                }
    }
}

