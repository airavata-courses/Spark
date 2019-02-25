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
			sh 'ssh ubuntu@149.165.170.39 rm -rf /home/ubuntu/Spark/'
			sh 'ssh ubuntu@149.165.170.39 mkdir -p /home/ubuntu/Spark/'
			sh 'scp -r /var/lib/jenkins/jobs/search-build-test-deploy/lastSuccessful/archive/search/target/search-0.0.1-SNAPSHOT.jar ubuntu@149.165.170.39:/home/ubuntu/Spark/'
                } 	
    }
}
