pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'sudo apt-get install python3-pip -y'
                sh 'pip3 install -r ./suggestion/requirements.txt'             
            }
        }
    }
}
