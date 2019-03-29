pipeline {
    agent any
    environment{
        LOCAL_KUBERNETES_IP = "${env.KUBERNETES_IP}"
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
                                    sudo docker tag suggestion aralshi/suggestion:1.0.0
                		    sudo docker push aralshi/suggestion:1.0.0
  		                 '''
                  }
      }
	    stage('deploy') {
		    steps{
		    sh '''
            JENKINS_NODE_COOKIE=dontKillMe nohup ssh -tt ubuntu@$LOCAL_KUBERNETES_IP '
            rm -r Spark_suggestion
            git clone https://github.com/airavata-courses/Spark.git Spark_suggestion
            cd Spark_suggestion/
            git checkout develop-suggestion_service
            sudo kubectl delete deployment suggestion
            sudo kubectl apply -f suggestionDeployment.yml
          '
          '''
		    }
	    }
    }
}
