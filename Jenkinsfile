pipeline {
    agent any

    environment {
        ZAP_HOME = 'C:\\Program Files\\ZAP\\Zed Attack Proxy'
        BACKEND_JAR = 'Identity-Service\\target\\IdentityService-0.0.1-SNAPSHOT.jar'
    }

    stages {
        stage('Build Project') {
            steps {
                dir('Identity-Service') {
                    bat 'mvnw.cmd clean package -DskipTests'
                }
            }
        }

        stage('Start Backend API') {
    steps {
        powershell """
            Start-Process -FilePath "java" -ArgumentList "-jar ${BACKEND_JAR}" -WindowStyle Hidden
        """
        sleep time: 20, unit: 'SECONDS'
    }
}


        stage('Start ZAP Proxy') {
            steps {
                dir("${env.ZAP_HOME}") {
                    bat '''
                        powershell -Command "Start-Process 'zap.bat' -ArgumentList '-daemon -port 8090 -config api.disablekey=true -config scripts.scriptsAutoLoad=true' -WindowStyle Hidden"
                    '''
                }
                sleep time: 20, unit: 'SECONDS'
            }
        }

        stage('Check PORT') {
            steps {
                bat '''
                    echo === Checking if ZAP (port 8090) is running ===
                    netstat -ano | findstr :8090 || echo ZAP proxy not listening on port 8090!

                    echo === Checking if Backend API (port 8080) is running ===
                    netstat -ano | findstr :8080 || echo Backend API not listening on port 8080!
                '''
            }
        }

        stage('Testcase 1: Get other user information.') {
            steps {
                bat """
                    curl -x http://127.0.0.1:8090 ^
                         -X GET http://127.0.0.1:8080/user/getById/2
                """
            }
        }

        stage('Append log vào workspace') {
    steps {
        bat '''
            type "C:\\Xanh\\tttn\\demo_offical\\zap\\zap-reports\\access.log" >> zap\\zap-reports\\access.log
        '''
    }
}


        stage('Check Vulnerable') {
    steps {
        script {
            def logPath = "zap/zap-reports/access.log"
            if (!fileExists(logPath)) {
                error("Log file not found: ${logPath}")
            }

            def content = readFile(logPath)
		echo "Log:\n" + content

            if (content.contains("BOLA vulnerability")) {
                error("BOLA vulnerability detected in latest scan! Failing pipeline.")
            } else {
                echo "No BOLA vulnerabilities detected in latest scan."
            }
        }
    }
}

    }

    post {
        always {
            publishHTML(target: [
                reportDir: "zap\\zap-reports",
                reportFiles: "access.log",
                reportName: 'Report'
            ])
        }
    }
}
