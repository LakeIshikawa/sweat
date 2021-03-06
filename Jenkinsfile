#!/usr/bin/env groovy
node {
    // Determine version properties
    switch ("${env.BRANCH_NAME}") {
        case 'master':
            rstage = 'final'
            repo = "${env.RELEASES_REPO}"
            break
        case 'devel':
            rstage = 'dev'
            repo = "${env.SNAPSHOTS_REPO}"
            break
    }

    // Build
    stage('build') {
        deleteDir()
        checkout scm

        // Make gradlew executable
        sh 'chmod 777 ./gradlew'
        sh 'git config user.email "lakeishikawa@gmail.eu"'
        sh 'git config user.name "LakeIshikawa"'
        sh 'git config core.fileMode false'

        // Grab build version!
        version = sh(
                script:"./gradlew -Prelease.stage=$rstage -Prelease.scope=patch", returnStdout: true).trim().split('\n')[1]
        version = "v" + version.substring(version.indexOf('version: ') + 9);

        currentBuild.displayName = version

        sh "./gradlew dist -Prelease.stage=$rstage -Prelease.scope=patch"
    }

    // Unit test
    stage('unit') {
        sh "./gradlew check -Prelease.stage=$rstage -Prelease.scope=patch"
    }

    // Deploy to archiva
    if( "${env.BRANCH_NAME}".toString() == "master" || "${env.BRANCH_NAME}".toString() == "devel" ) {
        stage('deploy') {
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus', passwordVariable: 'PASS', usernameVariable: 'USER']]) {
                sh "./gradlew publish -Prelease.stage=$rstage -Prelease.scope=patch -PpublishURL=$repo"
            }
        }
    }

    // Release
    if( "${env.BRANCH_NAME}".toString() == "master" ){
        stage('release') {
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'bitbucket', passwordVariable: 'PASS', usernameVariable: 'USER']]) {
                sh "./gradlew release -Prelease.stage=final -Prelease.scope=patch -Dorg.ajoberstar.grgit.auth.password=$PASS -Dorg.ajoberstar.grgit.auth.username=$USER"
            }
        }
    }
}