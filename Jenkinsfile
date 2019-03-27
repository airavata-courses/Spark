pipeline {
    agent any
	environment {
		LOCAL_RATING_IP = "${env.RATING_IP}"
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
                    sudo docker tag $id aralshi/rating:0.0.1
		    sudo docker push aralshi/rating:0.0.1
		    '''
            }
	    
	}
	    stage('deploy') {
		    steps{
		    sh 'JENKINS_NODE_COOKIE=dontKillMe nohup ssh -tt ubuntu@$LOCAL_RATING_IP sudo docker run --rm -d -p 8080:8080 aralshi/rating:0.0.1'
		    }
	    }
    }
    post {
        success{
			echo 'Successful!!'
		    /*archiveArtifacts artifacts: 'rating/target/rating-0.0.1-SNAPSHOT.jar'
		    sh '''
		        ssh ubuntu@$LOCAL_RATING_IP rm -rf /home/ubuntu/Spark/
			ssh ubuntu@$LOCAL_RATING_IP mkdir -p /home/ubuntu/Spark/
		        JENKINS_NODE_COOKIE=dontKillMe scp -r /var/lib/jenkins/workspace/rating-build-test-deploy/rating/target/rating-0.0.1-SNAPSHOT.jar ubuntu@$LOCAL_RATING_IP:/home/ubuntu/Spark/
			nohup ssh ubuntu@$LOCAL_RATING_IP '
			sudo apt update
			sudo apt install default-jdk -y
			sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password root"
                        sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password root"
                        sudo DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server-5.7
                        sudo mysql -uroot -proot -e "create database if not exists movie"
                        sudo mysql -uroot -proot -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root'"
			killall -9 java || true'
			ssh -f ubuntu@$LOCAL_RATING_IP java -jar -Durl.RATING_IP=$LOCAL_RATING_IP /home/ubuntu/Spark/rating-0.0.1-SNAPSHOT.jar
		    '''*/
                }
    }
}

