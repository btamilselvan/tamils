> Uses the TCP connections.
> The first request is similar to http but gets upgraded to websocket to have a persistent connection.
> persistent connections

# Spring-boot Elements:

> STOMP protocol (over websocket) and STOMP messages
> Message-handling controller
	> @MessageMapping - (just like /path), mapped to a specific end-point (to receive messages from clients).
	> @SendTo - send reply to a specific destination. Client subscribe to this destination to receive message from server.
> WebsocketMessageBroker
	> @EnableWebSocketMessageBroker - to enable websocket messaging. The configuration class to enable message brokers, specify destinations and message prefixes, if any. Also, enables and registers the stomp end-points, and fall-back mechanism.
User to User messages:
> use SimpMessagingTemplate.convertAndSendToUser method in MessageHandlers
> send the "To user" in the message and pass the to user to convertAndSendToUser method. the destination prefix should be /user (configurable in the message broker)
> subscribe to user specific messages in the web (something like /user/{username}/destination
	
# Security
> Configure websecurity and websocket security
> Allow connect requests go thru spring websecurity.
> Make the websocket security filter high precedence to take control over the inbound channel validation
> obtain token using regular login workflow and send the token in websocket request headers
> read the headers in websocket security filter (configureClientInboundChannel) and validate the token for CONNECT commands


	
	 

 