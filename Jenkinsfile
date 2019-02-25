pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'npm install'
            }
        }

       }
    post {
        success{
             echo "successful build!!"
        }

      }
    }
