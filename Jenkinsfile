pipeline {
    agent any
    stages {
        stage('install dependencies') {
            steps {
                sh 'sudo apt-get install maven -y'
		        sh 'mvn --version'
		        sh 'export DEBIAN_FRONTEND="noninteractive"'
		    	sh 'sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password $password"'
		    	sh 'sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password $password"'
		    	sh 'sudo apt-get install -y mysql-server'
		    	sh 'mysql_secure_installation'
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
                }
    }
}

