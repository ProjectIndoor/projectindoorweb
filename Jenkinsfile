pipeline {
   agent any
   stages {
       stage('Preparation') { // for display purposes
          steps {
              // Get code from GitHub repository
              git 'https://github.com/ProjectIndoor/projectindoorweb'
          }
       }
       stage('Build') {
          steps {
              script { //wrap in script to make it compatible with blue ocean syntax
                  // Run the gradle build
                  if (isUnix()) {
                     sh "./gradlew clean clientInstall build --info"
                  } else {
                     bat "./gradlew.bat clean clientInstall build --info"
                  }
              }
          }
       }
       stage('Check') {
          steps{
              // Run checkstyle and show results
              step([$class: "CheckStylePublisher",
                    canComputeNew: false,
                    defaultEncoding: "",
                    healthy: "",
                    pattern: "build/reports/checkstyle/main.xml",
                    unHealthy: ""])
              findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: 'build/reports/findbugs/main.xml', unHealthy: ''
              pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'build/reports/pmd/main.xml', unHealthy: ''
          }
       }
   }
   post {
       failure {
            slackSend color: '#FF5555', message: 'Build failed!'
       }

   }
}
