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
	    stage('build docker') {
		steps {
                    sh '''
		    sudo docker build . -t suggestion
		    sudo docker login --username=aralshi --password=indiatrip2019 || true
                    id=$(sudo docker images | grep -E 'suggestion' | awk -e '{print $3}')
                    sudo docker tag $id aralshi/suggestion:1.0.0
		    sudo docker push aralshi/suggestion:1.0.0
		    '''
            }
	    
	}
	    stage('deploy') {
		    steps{
		    sh 'JENKINS_NODE_COOKIE=dontKillMe nohup ssh -tt ubuntu@$LOCAL_SUGGEST_IP sudo docker run --rm -d -p 5000:5000 aralshi/suggestion:1.0.0'
		    }
	    }
		/*stage('Deploy') {
			steps {
				sh '''
					JENKINS_NODE_COOKIE=dontKillMe ssh ubuntu@$LOCAL_SUGGEST_IP '
						killall -9 python3 || true
						rm -r Spark
						git clone https://github.com/airavata-courses/Spark.git
						cd Spark
						git checkout develop-suggestion_service
						cd suggestion
						sudo apt-get install python3-pip -y
						pip3 install -r requirements.txt
				        	'
					JENKINS_NODE_COOKIE=dontKillMe nohup ssh -f ubuntu@$LOCAL_SUGGEST_IP python3 Spark/suggestion/suggestion/app.py -ip $LOCAL_SUGGEST_IP
					       
				'''
			}
		}*/
    }
}
