def builders = [
	"build": {
		node {
            stage('Build') {
                sh 'mvn clean package'
            }
        }
	},
	"javadoc": {
	    stage('Documentation') {
            sh 'mvn site'
        }
	}
]
stage('Preparation'){
    git credentialsId: 'Awia00', url: 'https://github.com/Awia00/gildedrose'
}
stage('parallel'){
	parallel builders
}
stage('Results'){
    junit '**/target/surefire-reports/TEST-*.xml'
    archiveArtifacts 'target/gildedrose-*.jar'
    archiveArtifacts 'target/site/**'
}
