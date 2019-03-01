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
    }
    post {
        success{
                   	archiveArtifacts artifacts: 'search/target/search-0.0.1-SNAPSHOT.jar'
			echo env.LOCAL_SEARCH_IP
		        sh 'ssh ubuntu@$LOCAL_SEARCH_IP sudo apt update'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP sudo apt install default-jdk -y'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP rm -rf /home/ubuntu/Spark/'
			sh 'ssh ubuntu@$LOCAL_SEARCH_IP mkdir -p /home/ubuntu/Spark/'
			sh 'scp -r /var/lib/jenkins/workspace/search-build-test-deploy/search/target/search-0.0.1-SNAPSHOT.jar ubuntu@$LOCAL_SEARCH_IP:/home/ubuntu/Spark/'
                	sh 'ssh ubuntu@$LOCAL_SEARCH_IP killall -9 java'
			sh 'ssh -f ubuntu@$LOCAL_SEARCH_IP java -jar /home/ubuntu/Spark/search-0.0.1-SNAPSHOT.jar'
		} 	
    }
}
