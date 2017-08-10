node {
    stage('Preparation'){
        checkout scm
        stash includes: '**', name: 'repository'
    }
}

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