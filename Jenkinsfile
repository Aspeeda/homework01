pipeline {
    agent { label 'maven' }

//     environment {
//         TELEGRAM_BOT_TOKEN = credentials('telegram_bot_token')
//     }

    stages {
        stage('Checkout Repository') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/Aspeeda/homework01.git'
                    ]]
                ])
            }
        }

        stage('Run UI Tests') {
            steps {
                sh './gradlew test'
            }
        }

//         stage('Generate Allure Report') {
//             steps {
//                 sh './gradlew allureReport'
//                 allure([
//                     results: [[path: 'build/allure-results']]
//                 ])
//             }
//         }
//     }

//     post {
//         always {
//             junit 'build/test-results/**/*.xml'
//         }
//         failure {
//             sh 'echo "Tests failed. Sending report to Telegram..."'
//             sh './send_report.sh'
//         }
    }
}