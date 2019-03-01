pipeline {
    agent any
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
			echo env.SEARCH_IP
		        sh 'ssh ubuntu@${env.SEARCH_IP} sudo apt update'
			sh 'ssh ubuntu@149.165.170.39 sudo apt install default-jdk -y'
			sh 'ssh ubuntu@149.165.170.39 rm -rf /home/ubuntu/Spark/'
			sh 'ssh ubuntu@149.165.170.39 mkdir -p /home/ubuntu/Spark/'
			sh 'scp -r /var/lib/jenkins/workspace/search-build-test-deploy/search/target/search-0.0.1-SNAPSHOT.jar ubuntu@149.165.170.39:/home/ubuntu/Spark/'
                	sh 'ssh ubuntu@149.165.170.39 killall -9 java'
			sh 'ssh -f ubuntu@149.165.170.39 java -jar /home/ubuntu/Spark/search-0.0.1-SNAPSHOT.jar'
		} 	
    }
}
