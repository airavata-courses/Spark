pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                dir("findyournextmovie") {
                    sh 'npm install'
                }
            }
        }
        stage('Deploy') {
            steps {
              sh '''
                JENKINS_NODE_COOKIE=dontKillMe nohup ssh -f ubuntu@149.165.170.119 '
                    sudo apt-get install nodejs-legacy
                    sudo apt-get install npm
                    sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password root"
                    sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password root"
                    sudo DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server-5.7
                    sudo mysql -uroot -proot -e "create database if not exists movie"
                    sudo mysql -uroot -proot -e "CREATE TABLE IF NOT EXISTS movie.users (user_id varchar(36), first_name VARCHAR(20), last_name VARCHAR(20), email VARCHAR(50), password VARCHAR(100), dob DATE, gender CHAR(1), country VARCHAR(20), created DATE, modified DATE)"
                    rm -r Spark
                    git clone https://github.com/airavata-courses/Spark.git
                    cd Spark/
                    git checkout develop-login_service
                    cd findyournextmovie/
                    npm install
                    npm start
                '
              '''
            }
        }
       }
    post {
        success{
             echo "successful build!!"
        }

      }
    }
