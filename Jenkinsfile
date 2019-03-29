pipeline {
    agent any
    
    	environment {
			LOCAL_KUBERNETES_IP = "${env.KUBERNETES_IP}"
        }
    stages {
        stage('Build') {
            steps {
                dir("findyournextmovie") {
                    sh 'npm install'
                }
            }
        }
		stage('Build Docker') {
			steps {
				sh '''
				sudo docker build . -t login
				sudo docker login --username=aralshi --password=indiatrip2019 || true
						id=$(sudo docker images | grep -E 'login' | awk -e '{print $3}')
						sudo docker tag login aralshi/login:1.0.0
				sudo docker push aralshi/login:1.0.0
				'''
			}
		}
        stage('Deploy') {
            steps{
				sh '''
					JENKINS_NODE_COOKIE=dontKillMe nohup ssh -tt ubuntu@$LOCAL_KUBERNETES_IP '
						rm -r Spark_login
						git clone https://github.com/airavata-courses/Spark.git Spark_login
						cd Spark_login/
						git checkout develop-login_service
						sudo kubectl delete deployment login
						sudo kubectl apply -f loginDeployment.yml
					'
				'''


		    }
        }
       }
    post {
        success{
             echo "successful build!!"
        }

      }
    }
