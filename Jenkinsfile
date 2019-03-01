pipeline {
    agent any
    environment{
        LOCAL_SUGGEST_IP = "${env.SUGGEST_IP}"
    }
    stages {
        stage('Build') {
            steps {
                sh 'sudo apt-get install python3-pip -y'
                sh 'pip3 install -r ./suggestion/requirements.txt'             
            }
        }
		stage('Deploy') {
			steps {
				sh '''
					JENKINS_NODE_COOKIE=dontKillMe ssh ubuntu@$LOCAL_SUGGEST_IP '
						killall -9 python3
						rm -r Spark
						git clone https://github.com/airavata-courses/Spark.git
						cd Spark
						git checkout develop-suggestion_service
						cd suggestion
						sudo apt-get install python3-pip -y
						pip3 install -r requirements.txt
				        	'
						ssh -f ubuntu@$LOCAL_SUGGEST_IP python3 Spark/suggestion/suggestion/app.py -ip $LOCAL_SUGGEST_IP
					       
				'''
			}
		}
    }
}
