pipeline {
    agent any
	environment {
		LOCAL_KUBERNETES_IP = "${env.KUBERNETES_IP}"
	}
    stages {
        stage('install dependencies') {
            steps {
                sh 'sudo apt-get install maven -y'
		sh 'mvn --version'
            }
        }
        stage('build maven') {
            steps {
                dir("search") {
                    sh 'pwd'
                    sh 'mvn clean install'
                    sh 'mvn install package'
                }
            }
        }
	       stage('build docker') {
          		steps {
                        sh '''
                    		    sudo docker build . -t search
                    		    sudo docker login --username=aralshi --password=indiatrip2019 || true
                                        id=$(sudo docker images | grep -E 'search' | awk -e '{print $3}')
                                        sudo docker tag $id aralshi/search:1.0.0
                    		    sudo docker push aralshi/search:1.0.0
        		            '''
              }
	        }
	    stage('deploy') {
          steps {
          sh '''
              JENKINS_NODE_COOKIE=dontKillMe ssh ubuntu@$LOCAL_KUBERNETES_IP '
              rm -r Spark_search
              git clone https://github.com/airavata-courses/Spark.git Spark_search
              cd Spark_search/
              git checkout develop-search_service
              sudo kubectl delete deployment search
              sudo kubectl apply -f searchDeployment.yml
            '
            '''
          }
	    }
    }
    post {
        success{
		        echo 'Successful!!'
		    }
    }
}
