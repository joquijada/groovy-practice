def ls = ['a b c', 'd e f', 'g h i']
ls*.split(' ')[0..1]

def out = '''
NAME                           LOCATION     MASTER_VERSION       MASTER_IP        MACHINE_TYPE    NODE_VERSION         NUM_NODES  STATUS
pplus-app-dev-usc1-cluster     us-central1  1.28.11-gke.1019001  35.193.200.255   n1-standard-32  1.28.11-gke.1019001  181        RUNNING
pplus-observ-dev-usc1-cluster  us-central1  1.28.11-gke.1019001  34.133.32.128    n1-standard-16  1.28.11-gke.1019001  6          RUNNING
pplus-tools-cluster            us-central1  1.28.11-gke.1019001  34.69.129.102    n2-highmem-16   1.28.11-gke.1019001  19         RUNNING
pplus-app-dev-use1-cluster     us-east1     1.28.11-gke.1019001  104.196.158.207  n1-standard-32  1.28.11-gke.1019001  133        RUNNING'''

out.split('\n').findResults {
  def fields = it.split()
  if (fields.size() < 2 || fields.contains('NAME')) {
    return null
  }
  fields[0..1]
}