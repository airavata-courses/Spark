pipeline {
    agent any

    stages {
        stage('Build React App') {
            steps {
                sh 'sudo npm install -g create-react-app'
                dir('findyournextmovie'){
                   sh 'npm install'
                   sh 'npm start'
                }
            }
        }
    }
}
