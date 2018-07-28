node {
       stage('Checkout'){
          checkout scm
       }

        stage('Build') {
                sh 'sh gradlew build -x test'
        }
        stage('Test') {
                sh 'sh gradlew test'
        }
        stage('Archive Artifacts') {
                archiveArtifacts artifacts: '**/libs/*.jar', fingerprint: true
        }

        stage('Build Dev Docker Image') {
            sh 'docker build . -t service-base'
        }


        stage('Deploy image') {
            def IMAGE_BASE_NAME="sofka/service-base"
            def DOCKER_IMAGE="$IMAGE_BASE_NAME:$BUILD_NUMBER"

            sh "docker tag upet-identifications $DOCKER_IMAGE"
            sh "docker push $DOCKER_IMAGE"

            sh "docker tag upet-identifications $IMAGE_BASE_NAME"
            sh "docker push $IMAGE_BASE_NAME"

            sh "docker tag upet-identifications $IMAGE_BASE_NAME:dev"
            sh "docker push $IMAGE_BASE_NAME:dev"

        }

}
