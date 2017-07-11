# emr-scheduler

## Setting

```json
{
  "emr": {
    "clusterName": "EMR Test",
    "emrVersion": "emr-5.6.0",
    "applications": [
      "hive",
      "spark"
    ],
    "serviceRole": "EMR_DefaultRole",
    "jobFlowRole": "EMR_EC2_DefaultRole",
    "keyName": "emr-test",
    "resources": {
      "master": {
        "type": "r4.xlarge",
        "securityGroup": "sg-df893eb9"
      },
      "slave": {
        "type": "r4.xlarge",
        "securityGroup": "sg-818a3de7",
        "instanceCount": 2
      }
    }
  }
}
```
# emr-scheduler
