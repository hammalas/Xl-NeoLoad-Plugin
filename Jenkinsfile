@Library('jenkins-groovy-lib')
import startNlWebIndus
startNlWebIndus()

stage('Archive files') {
    sh 'mv -f ./neoload-xebia-plugin/target/*.jar ./'
    archiveArtifacts artifacts: '**/*', excludes: 'xebia-release-plugin/**/*'
}