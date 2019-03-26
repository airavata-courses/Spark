pipeline {
    agent any
    
    	environment {
            LOCAL_LOGIN_IP = "${env.LOGIN_IP}"
        }
    stages {
        stage('Build') {
            steps {
                dir("findyournextmovie") {
                    sh 'npm install'
                }
            }
        }
		stage('build docker') {
		steps {
            sh '''
		    sudo docker build . -t login
		    sudo docker login --username=aralshi --password=indiatrip2019 || true
                    id=$(sudo docker images | grep -E 'login' | awk -e '{print $3}')
                    sudo docker tag login aralshi/login:1.0.0
		    sudo docker push aralshi/login:1.0.0
		    '''
        }
        stage('deploy') {
		    steps{
		    sh 'JENKINS_NODE_COOKIE=dontKillMe nohup ssh -tt ubuntu@$LOCAL_LOGIN_IP sudo docker run --rm -d -p 8080:8080 aralshi/login:1.0.0'
		    }
	    }
       }
    post {
        success{
             echo "successful build!!"
        }

      }
    }
