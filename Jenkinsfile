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
                dir("serviceRegistry") {
                    sh 'pwd'
                    sh 'mvn clean install'
                    sh 'mvn install package'
                }
            }
        }
    }
    post {
        success{
                   	archiveArtifacts artifacts: 'serviceRegistry/target/serviceRegistry-0.0.1-SNAPSHOT.jar'
			sh 'ssh ubuntu@149.165.169.102 sudo apt update'
			sh 'ssh ubuntu@149.165.169.102 sudo apt install default-jdk -y'
			sh 'ssh ubuntu@149.165.169.102 rm -rf /home/ubuntu/Spark/'
			sh 'ssh ubuntu@149.165.169.102 mkdir -p /home/ubuntu/Spark/'
			sh 'scp -r /var/lib/jenkins/workspace/serviceRegistry-build-test-deploy/serviceRegistry/target/serviceRegistry-0.0.1-SNAPSHOT.jar ubuntu@149.165.169.102:/home/ubuntu/Spark/'
                	sh 'ssh ubuntu@149.165.169.102 killall -9 java'
			sh 'ssh ubuntu@149.165.169.102 java -jar /home/ubuntu/Spark/serviceRegistry-0.0.1-SNAPSHOT.jar'
		}
    }
}
