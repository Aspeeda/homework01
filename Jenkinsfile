pipeline {
    agent { label 'maven' }

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Выбор браузера (chrome/firefox)')
        string(name: 'BRANCH', defaultValue: 'main', description: 'Выбор ветки для тестов')
    }

    environment {
        GRADLE_OPTS = "-Dorg.gradle.daemon=false"
        ALLURE_RESULTS = "build/allure-results"
    }

    stages {
        stage('Checkout Repository') {
            steps {
                git branch: "${params.BRANCH}",
                    url: 'https://github.com/Aspeeda/homework01.git',
                    credentialsId: 'jenkins'
            }
        }

        stage('Setup Dependencies') {
            sh 'chmod +x gradlew'
            sh './gradlew dependencies'
        }

        stage('Run UI Tests') {
            steps {
                sh "./gradlew clean test -Dbrowser=${params.BROWSER}"
            }
        }

      stage('Run UI Tests') {
          sh './gradlew test'
      }

      stage('Generate Allure Report') {
          sh './gradlew allureReport'
          allure([
              results: [[path: 'build/allure-results']]
          ])
      }

    post {
        always {
            junit '**/build/test-results/**/*.xml'
            archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
        }

        failure {
            script {
                def message = "Шеф, усе пропало"
             sh "curl -X POST -H 'Content-Type: application/json' -d '{\"chat_id\": \"$chatId\", \"text\": \"$(getTelegramReport()\"}' https://api.telegram.org/bot$token/sendMessage"
            }
        }
    }
}