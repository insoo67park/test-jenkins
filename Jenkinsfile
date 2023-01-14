// pipeline for development environment
pipeline {
    environment {
        PRIVATE_IMAGE_REGISTRY = "192.168.56.1:5000"
        IMAGE_NAME = "3dx-conv-worker"
        IMAGE_REPO_CREDENTIALS = credentials("private-image-registry")
        BUILD_NUMBER = "v0.0.1"
        containerImage = ''
    }
    
    agent any
    tools {
        jdk "openjdk17"
        //maven "maven-3.6.3"
    }
    
    stages {
        stage('Initialize') {
            steps {
                sh 'echo PATH = ${PATH}'
                sh 'echo M2_HOME = ${M2_HOME}'
                sh 'echo JAVA_HOME = ${JAVA_HOME}'
            }
        }
        
        stage('Maven build') {
            steps {
                sh 'echo maven clean and build'
                sh 'mvn -version'
                sh 'javac -version'
                sh 'mvn clean'
                sh 'mvn -Dmaven.test.failure.ignore=true install assembly:single'
            }
            //post {
            //    success {
            //        junit 'target/surefire-reports/**/*.xml'
            //    }
            //}
        }
        
        stage('Build container image') {
            steps {
                script {
                    sh 'docker build -t $PRIVATE_IMAGE_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER .'
                    sh 'docker build -t $PRIVATE_IMAGE_REGISTRY/$IMAGE_NAME:latest .'
                }
            }
        }
        
        stage('Login to container registry') {
            steps {
                sh 'echo $IMAGE_REPO_CREDENTIALS_PSW | docker login -u $IMAGE_REPO_CREDENTIALS_USR --password-stdin $PRIVATE_IMAGE_REGISTRY'
            }
        }
        
        stage('Push container image') {
            steps {
                script {
                    sh 'docker push $PRIVATE_IMAGE_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER'
                    sh 'docker push $PRIVATE_IMAGE_REGISTRY/$IMAGE_NAME:latest'
                }
            }
        }
        
        stage('Cleaning up') {
            steps {
                sh 'docker rmi $PRIVATE_IMAGE_REGISTRY/$IMAGE_NAME:$BUILD_NUMBER'
                sh 'docker rmi $PRIVATE_IMAGE_REGISTRY/$IMAGE_NAME:latest'
            }
        }
    }
}
