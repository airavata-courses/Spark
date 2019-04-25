pipeline {
    agent any
	environment {
		LOCAL_KUBERNETES_IP = "${env.KUBERNETES_IP}"
		LOCAL_KUBERNETES_TACC_IP = "${env.KUBERNETES_TACC_IP}"
	}
    stages {
        stage('install dependencies') {
            steps {
                	sh 'sudo apt-get install maven -y'
		        sh 'mvn --version'
		    	sh 'sudo DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server-5.7'
		    	sh 'sudo mysql -uroot -proot -e "create database if not exists movie"'
            }
        }
        stage('build maven') {
            steps {
                dir("rating") {
                    sh 'pwd'
                    sh 'mvn clean install'
                    sh 'mvn install package'
                }
            }
        }
		stage('build docker') {
		steps {
                    sh '''
		    sudo docker build . -t rating
		    sudo docker login --username=aralshi --password=indiatrip2019 || true
                    id=$(sudo docker images | grep -E 'rating' | awk -e '{print $3}')
                    sudo docker tag rating aralshi/rating:0.0.1
		    sudo docker push aralshi/rating:0.0.1
		    '''
            }
	    
	}
	    stage('deploy') {
		    steps{
		    	sh '''
            			JENKINS_NODE_COOKIE=dontKillMe ssh ubuntu@$LOCAL_KUBERNETES_IP '
            				rm -r Spark_rating
            				git clone https://github.com/airavata-courses/Spark.git Spark_rating
            				cd Spark_rating/
            				git checkout develop-user_rating_management_service
            				sudo kubectl delete deployment rating
            				sudo kubectl apply -f ratingDeployment.yml
          			'
          		'''
			sh '''
            			JENKINS_NODE_COOKIE=dontKillMe ssh ubuntu@$LOCAL_KUBERNETES_TACC_IP '
            				rm -r Spark_rating
            				git clone https://github.com/airavata-courses/Spark.git Spark_rating
            				cd Spark_rating/
            				git checkout develop-user_rating_management_service
            				sudo kubectl delete deployment rating
            				sudo kubectl apply -f ratingDeployment.yml
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

