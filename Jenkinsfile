pipeline {
    agent any

    stages {
        stage('Build React App') {
            steps {
                sh 'sudo apt-get install nodejs-legacy'
                sh 'sudo apt-get install npm'
                dir('findyournextmovie'){
                   sh 'npm install'
                }
            }
        }
        
        stage('Deploy') {
			steps {
				sh '''
                    JENKINS_NODE_COOKIE=dontKillMe nohup ssh -f ubuntu@149.165.157.231 '
                        sudo apt-get install nodejs-legacy
                        sudo apt-get install npm
                        rm -r Spark
                        git clone https://github.com/airavata-courses/Spark.git
                        cd Spark/
                        git checkout develop-react_UI
                        cd findyournextmovie/
                        npm install
                        npm start
					' 
				'''
			}
		}
    }
}
