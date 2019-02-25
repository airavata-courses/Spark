pipeline {
    agent any
    stages {
        stage('install dependencies') {
            steps {
                	sh 'sudo apt-get install maven -y'
		        sh 'mvn --version'
			sh 'sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password root"'
			sh 'sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password root"'
		    	sh 'sudo DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server-5.7'
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

