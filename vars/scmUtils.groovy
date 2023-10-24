def gitCheckout(Map params) {

    checkout([
        $class: 'GitSCM',
        branches: [[name:  params.branch ]],
        userRemoteConfigs: [[ url: params.url, credentialsId: params.credentialsId ]],
        extensions: [[$class: 'LocalBranch']]
    ])
}


def setGitUserInfo(String email, String username) {
  sh "git config user.name ${username}"
  sh "git config user.email ${email}"
}

def gitCommit(Map params) {
  sh "git add ${params.path}"
  sh "git commit -m ${params.msg}"
}

def gitPush(Map params) {
    withCredentials([usernamePassword(credentialsId: params.credentialsId, usernameVariable: 'username', passwordVariable: 'password')])
      {
        sh "git push --set-upstream https://$password@github.com/${params.gitHubPath}.git ${params.branch}"
      }
}
