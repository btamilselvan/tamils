- docker pull rabbitmq

- docker run -d --hostname my-rabbit --name rq -p 5672:5672 rabbitmq

- docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

- $ docker run -d --hostname my-rabbit-host -p 5672:5672 -p 15672:15672 --name rabbitq rabbitmq:3-management

#####components
message -> exchange -> queue

- messages are sent to exchanges -> then forwared to queues based on type of exchange
- topic exchange
- fanout exchange
- direct exchange
- headers exchange

##### AMQP - Advanced Message Queuing protocol
RabbitMq, OpenAMQ, Storm AMQP

 docker run -d --hostname my-rabbit-host -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management

- Access the management portal using 15672 port - default username/password - guest/guest

### springboot configuration

#### Publisher (sender) minimal configuration
- Topic exchange, credentials, server host (can be part of application.properties)
- RabbitTemplate bean -> to bind routing key and exchange
- MessageConverter bean (for json)

#### Receiver minimal configuration
- Topic exchange, queues (to listen), Topic to queue binding (TopicBinding) bean and MessageConverter bean (for json)
- The queues/topics will be created automaticall when receiver connects to Rabbitmq
- User @RabbitListener and specify queues to listen to receive messages.

- can have multiple queues bound to one topic (with multiple binding key)
- Use DeclareVariables bean to expose multiple queues, topic and their corresponding bindings 