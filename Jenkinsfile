pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'sudo apt-get install maven -y'
		sh 'mvn --version'
            }
        }
    }
}
