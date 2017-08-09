def builders = [
	"build": {
		node {
            unstash 'repository'
            sh 'mvn clean package'
            stash includes: 'target/**', name: 'jar'
        }
	},
	"javadoc": {
	    node {
            unstash 'repository'
            sh 'mvn site'
            stash includes: 'target/site/**', name: 'doc'
        }
	}
]

node {
    stage('Preparation'){
        git credentialsId: 'Awia00', url: 'https://github.com/Awia00/gildedrose'
        stash includes: '**', name: 'repository'
    }
}

stage('parallel'){
    parallel builders
}

node {
    stage('Results'){
        unstash 'jar'
        unstash 'doc'
        junit '**/target/surefire-reports/TEST-*.xml'
        archiveArtifacts 'target/gildedrose-*.jar'
        archiveArtifacts 'target/site/**'
    }
}