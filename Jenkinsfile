node {
    stage('Preparation'){
        git credentialsId: 'Awia00', url: 'https://github.com/Awia00/gildedrose'
    }
    stage('Build'){
        sh 'mvn clean package'
    }
    stage('Results'){
        junit '**/target/surefire-reports/TEST-*.xml'
        archiveArtifacts 'target/gildedrose-*.jar'
    }
    stage('Documentation'){
        sh 'mvn site'
        archiveArtifacts 'target/site'
    }
}
