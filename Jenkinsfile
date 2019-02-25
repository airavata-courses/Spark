pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir("findyournextmovie") {
                    sh 'npm install'
                }
            }
        }

       }
    post {
        success{
             echo "successful build!!"
        }

      }
    }
