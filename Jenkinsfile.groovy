pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Выберите браузер для запуска тестов')
        booleanParam(name: 'HEADLESS', defaultValue: false, description: 'Запускать тесты в headless-режиме?')
    }

//    environment {
//        BASE_URL = 'https://otus.ru'
//    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    sh """
                        mvn test -Dbrowser=${env.BROWSER} -DbaseUrl=${env.BASE_URL}
                    """
                }
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                        disabled: false,
                        includeProperties: false,
                        jdk: '',
                        report: './target/allure-results',
                        reportBuildPolicy: 'ALWAYS'
                ])
            }
        }
    }

    post {
        always {
            echo 'Тесты завершены.'
        }
    }
}