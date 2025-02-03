pipeline {
    agent any  // Использует любой доступный исполнитель для всего пайплайна

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Выберите браузер')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Checkout utils') {
            steps {
                dir('tools') {
                    git branch: 'main', url: 'https://github.com/Aspeeda/homework01.git', credentialsId: 'jenkins'
                }
            }
        }

        stage('Running UI tests') {
            steps {
                script {
                    status = sh(
                            script: "mvn test -DBROWSER=${params.BROWSER} -DBASE_URL=${env.BASE_URL}",
                            returnStatus: true
                    )

                    if (status > 0) {
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Publish allure report') {
            steps {
                allure([
                        disabled: false,
                        includeProperties: false,
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