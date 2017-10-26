pipeline {
   agent any
   def gradleHome
   stages {
       stage('Preparation') { // for display purposes
          steps {
              // Get code from GitHub repository
              git 'https://github.com/ProjectIndoor/projectindoorweb'
              // get Gradle
              gradleHome = tool name: 'DefaultGradle', type: 'gradle'
          }
       }
       stage('Build') {
          steps {
              // Run the gradle build
              if (isUnix()) {
                 sh "./gradlew clean build --info"
              } else {
                 bat "./gradlew.bat clean build --info"
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
       failed {3
            slackSend color: '#FF5555', message: 'Build failed: ${env.JOB_NAME} ${env.BUILD_NUMBER}'
       }

   }
}
