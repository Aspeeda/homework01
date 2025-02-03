timeout(time: 60, unit: 'MINUTES') {
    node('maven') {

        stage('Checkout') {
            checkout scm
        }

        stage('Checkout utils') {
            dir('tools') {
                git branch: 'main', url: 'https://github.com/Aspeeda/homework01.git', credentialID: 'jenkins'
            }
        }

        utils = load './tools/utils'
        utils.prepare_yaml_config()

        stage('Running UI tests') {

            status = sh(
                    script: "mvn test -DBROWSER=$env.BROWSER -DBASE_URL=$env.BASE_URL",
                    returnStatus: true
            )

            if(status>0) {
                currentBuild.status = 'UNSTABLE'
            }
        }

        stage('Publish allure report') {
            allure([
                    disabled: false,
                    includeProperties: false,
                    jdk: '',
                    report: './target/allure-results',
                    reportBuildPolicy: 'ALWAYS'
            ])
        }

        post {
            always {
                echo 'Тесты завершены.'
            }
        }
    }
}