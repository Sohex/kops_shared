#!/usr/bin/env groovy

def call(String kopsState = null, String clusterName = null, boolean getKubecfg = false) {
  // get kops and kubectl binaries
  sh 'wget -q -O kubectl https://storage.googleapis.com/kubernetes-release/release/\$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl'
  sh 'chmod +x ./kubectl'
  
  sh 'wget -q -O kops https://github.com/kubernetes/kops/releases/download/\$(curl -s https://api.github.com/repos/kubernetes/kops/releases/latest | grep tag_name | cut -d \'"\' -f 4)/kops-linux-amd64'
  sh 'chmod +x ./kops'

  if ( getKubecfg == true ) {
    sh "mkdir .kube"
    sh "./kops export kubecfg --name ${clusterName} --config=./kube/${clusterName}"
    sh "export KUBECONFIG=./kube/${clusterName}"
  }
}
