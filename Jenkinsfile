pipeline {
    agent any
    stages {
        stage('install dependencies') {
            steps {
                sh 'sudo apt-get install maven -y'
		        sh 'mvn --version'
			sh '/home/ubuntu/test_mysql.sh'
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

