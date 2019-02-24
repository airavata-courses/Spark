pipeline {
    agent any
    stages {
        stage('install dependencies') {
            steps {
                sh 'sudo apt-get install maven -y'
		        sh 'mvn --version'
            }
        }
        stage('build maven') {
            steps {
                dir(search) {
                    sh 'mvn clean install'
                    sh 'mvn install package'
                }
            }
        }
    }
    post {
        success{
                    archiveArtifacts artifacts: 'search/target/search-0.0.1-SNAPSHOT.jar'
                }
    }
}
