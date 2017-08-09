def builders = [
	"build": {
		node {
            unstash 'repository'
            sh 'mvn clean package'
            stash 'jar'
        }
	},
	"javadoc": {
	    node {
            unstash 'repository'
            sh 'mvn site'
            stash 'jar'
        }
	}
]
stage('Preparation'){
    git credentialsId: 'Awia00', url: 'https://github.com/Awia00/gildedrose'
    stash 'repository'
}
stage('parallel'){
	parallel builders
}
stage('Results'){
    unstash 'jar'
    unstash 'doc'
    junit '**/target/surefire-reports/TEST-*.xml'
    archiveArtifacts 'target/gildedrose-*.jar'
    archiveArtifacts 'target/site/**'
}
