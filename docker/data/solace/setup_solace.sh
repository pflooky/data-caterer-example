#!/bin/sh

host=solaceserver

echo "Creating new queue in Solace"
curl "http://$host:8080/SEMP/v2/config/msgVpns/default/queues" \
  -X POST \
  -u admin:admin \
  -H "Content-type:application/json" \
  -d '{ "queueName":"test_queue" }'

echo "Creating JNDI queue object"
curl "http://$host:8080/SEMP/v2/config/msgVpns/default/jndiQueues" \
  -X POST \
  -u admin:admin \
  -H "Content-type:application/json" \
  -d '{ "physicalName":"test_queue", "queueName":"/JNDI/Q/test_queue" }'

echo "Creating new topic in Solace"
curl http://$host:8080/SEMP/v2/config/msgVpns/default/topicEndpoints \
  -X POST \
  -u admin:admin \
  -H "Content-type:application/json" \
  -d '{ "topicEndpointName":"test_topic" }'

echo "Creating JNDI queue object"
curl http://$host:8080/SEMP/v2/config/msgVpns/default/jndiTopics \
  -X POST \
  -u admin:admin \
  -H "Content-type:application/json" \
  -d '{ "physicalName":"test_topic", "topicName":"/JNDI/T/test_topic" }'

echo "Creating subscription between queue and topic"
curl http://$host:8080/SEMP/v2/config/msgVpns/default/queues/test_queue/subscriptions \
  -X POST \
  -u admin:admin \
  -H "Content-type:application/json" \
  -d '{ "queueName":"test_queue", "subscriptionTopic":"test_topic" }'
