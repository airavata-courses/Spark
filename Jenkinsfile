pipeline {
    agent any

    stages {
        stage('Build React App') {
            steps {
                sh 'sudo apt-get install nodejs-legacy'
                sh 'sudo apt-get install npm'
                dir('findyournextmovie'){
                   sh 'npm install'
                }
            }
        }
        
        stage('Build Docker') {
			steps {
				sh '''
				sudo docker build . -t userinterface
				sudo docker login --username=aralshi --password=indiatrip2019 || true
						id=$(sudo docker images | grep -E 'userinterface' | awk -e '{print $3}')
						sudo docker tag userinterface aralshi/userinterface:1.0.0
				sudo docker push aralshi/userinterface:1.0.0
				'''
			}
		}
		
		stage('Deploy') {
            steps{
		    sh 'JENKINS_NODE_COOKIE=dontKillMe nohup ssh -tt ubuntu@149.165.157.231 sudo docker run --rm -d -p 3000:3000 aralshi/userinterface:1.0.0'
		    }
        }
    }
}
