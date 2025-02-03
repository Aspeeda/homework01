pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Выберите браузер')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check Current Path') {
            steps {
                sh 'pwd'
            }
        }

//        stage('Checkout utils') {
//            steps {
//                dir('tools') {
//                    git branch: 'main', url: 'https://github.com/Aspeeda/homework01.git', credentialsId: 'jenkins'
//                }
//            }
//        }

        stage('Running UI tests') {
            steps {
                script {
                    dir('path/to/your/project') {
                        status = sh(
                                script: "./gradlew test -DBROWSER=${params.BROWSER} -DBASE_URL=${env.BASE_URL}",
                                returnStatus: true
                        )

                        if (status > 0) {
                            currentBuild.result = 'UNSTABLE'
                        }
                    }
                }
            }
        }

//        stage('Publish Allure Report') {
//            steps {
//                allure([
//                        results: [[path: 'target/allure-results']],
//                        reportBuildPolicy: 'ALWAYS',
//                        includeProperties: false,
//                        report: './target/allure-report'
//                ])
//            }
//        }
    }

    post {
        always {
            echo 'Тесты завершены.'
        }
    }
}
