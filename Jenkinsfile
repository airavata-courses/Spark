pipeline {
    agent any
	environment {
		LOCAL_SEARCH_IP = "${env.SEARCH_IP}"
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
                    sudo docker tag $id aralshi/findyournextmovie:1.0.0
		    sudo docker push aralshi/findyournextmovie:1.0.0
		    '''
            }
	    
	}
	    stage('deploy') {
		    sh 'JENKINS_NODE_COOKIE=dontKillMe nohup ssh -tt ubuntu@149.165.157.60 sudo docker run --rm -d -p 8080:8080 aralshi/findyournextmovie:1.0.0'
	    }
    }
    post {
        success{
                   	/*archiveArtifacts artifacts: 'search/target/search-0.0.1-SNAPSHOT.jar'
			echo env.LOCAL_SEARCH_IP
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP export SEARCH_IP=$LOCAL_SEARCH_IP' 
		        sh 'ssh ubuntu@$LOCAL_SEARCH_IP sudo apt update'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP sudo apt install default-jdk -y'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP rm -rf /home/ubuntu/Spark/'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP mkdir -p /home/ubuntu/Spark/'
			sh 'scp -r /var/lib/jenkins/workspace/search-build-test-deploy/search/target/search-0.0.1-SNAPSHOT.jar ubuntu@$LOCAL_SEARCH_IP:/home/ubuntu/Spark/'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP killall -9 java || true'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP docker container rm -f search || true'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP sudo docker run -d -p 8080:8080 search'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP sudo docker build -t search:latest .'*/

		//			sh 'ssh -f ubuntu@$LOCAL_SEARCH_IP java -jar -Durl.SEARCH_IP=$LOCAL_SEARCH_IP /home/ubuntu/Spark/search-0.0.1-SNAPSHOT.jar'
		} 	
    }
}
