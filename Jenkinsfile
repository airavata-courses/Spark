pipeline {
    agent any

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
					JENKINS_NODE_COOKIE=dontKillMe nohup ssh ubuntu@149.165.156.78 '
						killall -9 python3
						rm -r Spark
						git clone https://github.com/airavata-courses/Spark.git
						cd Spark
						git checkout develop-suggestion_service
						cd suggestion
						sudo apt-get install python3-pip -y
						pip3 install -r requirements.txt
						cd suggestion/
						python3 app.py
					'
				'''
			}
		}
    }
}
