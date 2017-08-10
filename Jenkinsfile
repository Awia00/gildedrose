// Constants
// This is to ensure that a developer does not push to the wrong ready branch namespace.
TARGET_BRANCH_NAME = "master"
MERGE_BRANCH_PREFIX = "ready"
GIT_REPO= "git@github.com:Awia00/gildedrose.git"
SSH_AGENT_ID="Awia00"
// State
inputSHA = "" // Why can't Jenkins tell me this?!
authorEmail = "none"
authorName = "none"
authorFull = "none <none>"

// Utils
//Does the branch namespace contain "ready", and should therefore be regarded as an integration branch.
def shouldMerge() {
    return "${BRANCH_NAME}" ==~ /^${MERGE_BRANCH_PREFIX}\/.*/
}

def builders = [
	"build": {
		stage("build"){
			node {
				unstash 'repository'
				sh 'mvn clean package'
				stash includes: 'target/**', name: 'jar'
			}
		}
	},
	"javadoc": {
		stage("documentation"){
			node {
				unstash 'repository'
				sh 'mvn site'
				stash includes: 'target/site/**', name: 'doc'
			}
		}
	}
]


// THE PIPELINE

println "Parameters: branch: ${BRANCH_NAME}, target: ${TARGET_BRANCH_NAME}, merge: ${shouldMerge()}"

lock("pip") {
    // The following stages are locked to ensure that only one pipeline runs at
    // at a time. The stages in here must contain everything between and
    // including the original git checkout and the final git push of the merged
    // branch.

	node{
    	stage("Checkout") {
			deleteDir()
    		buildNumber = currentBuild.number;
      		//buildNumber = sh(script: "curl -sd '${TARGET_BRANCH_NAME}' ${MY_SQNZ_URL}", returnStdout: true).trim()
      		currentBuild.displayName = "${currentBuild.displayName} (${buildNumber})"
      		sshagent (credentials: ["${SSH_AGENT_ID}"]) 
			{
				timeout(1) {
					sh "git clone --no-checkout ${GIT_REPO} ."
				}
				inputSHA = sh(script: "git rev-parse origin/${BRANCH_NAME}", returnStdout: true).trim()
				authorName = sh(script: "git log -1 --format='%an' ${inputSHA}", returnStdout: true).trim()
				authorEmail = sh(script: "git log -1 --format='%ae' ${inputSHA}", returnStdout: true).trim()
				authorFull = "${authorName} <${authorEmail}>"

				timeout(2) {
					sh """
git config user.email "${authorEmail}"
git config user.name "${authorName}"
git checkout "${TARGET_BRANCH_NAME}"
if [ "\$(git branch --contains ${inputSHA} | wc -l)" -gt "0" ]
then
    echo "MERGE ERROR: origin/${BRANCH_NAME} already present in origin/${TARGET_BRANCH_NAME}"
    exit 1
fi
COMMITS="\$(git log --oneline ${TARGET_BRANCH_NAME}..${inputSHA} | wc -l)"
if [ "\${COMMITS}" -gt "1" ] || ! git merge --ff-only ${inputSHA}
then
    git reset --hard origin/${TARGET_BRANCH_NAME}
    git merge --no-ff --no-commit ${inputSHA}
    git commit --author "${authorFull}" --message "Merge branch '${BRANCH_NAME}' into '${TARGET_BRANCH_NAME}'"
fi
git submodule update --init --recursive
"""
				}
				sha = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
				shaish = sh(script: "git rev-parse --short=5 HEAD", returnStdout: true).trim()
			}
		}
	}

	// BUILD SHIT
	stash includes: '**', name: 'repository'
	parallel builders

	node{
		stage('Results'){
			unstash 'jar'
			unstash 'doc'
			junit '**/target/surefire-reports/TEST-*.xml'
			archiveArtifacts 'target/gildedrose-*.jar'
			archiveArtifacts 'target/site/**'
		}
		// Integrate
		stage("Push") {
			sshagent (credentials: ["${SSH_AGENT_ID}"]) {
				timeout(1) {
					sh """
					git push origin ${TARGET_BRANCH_NAME}
					git fetch
					if [ "\$(git rev-parse origin/${BRANCH_NAME})" = "${inputSHA}" ]
					then
					git push origin :${BRANCH_NAME}
					fi
					"""
				}
			}
			deleteDir()
		}
    }
	
}

