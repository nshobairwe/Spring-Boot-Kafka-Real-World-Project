#WINDOW USERS
#How to stark kafka broker
Ensure that Kafka and Zookeeper servers are running:

#Start Zookeeper:
command use this in kafka folder with new terminal:
zookeeper-server-start.bat config\zookeeper.properties

#Start Kafka:
command use this in kafka folder with new terminal:
kafka-server-start.bat config\server.properties

#Verify Messages are being published in kafka broker topic:
#Open another terminal window.
#Run the Kafka consumer to listen to the javaguides topic and verify that the messages are being received:
#command use this in kafka folder with new terminal:
kafka-console-consumer.bat --topic put-topic-name --from-beginning --bootstrap-server localhost:9092





ERROR FIXING
Kafka - Broker fails because all log dirs have failed
If you are on a Windows machine, you can use the following command

rmdir /s /q C:\tmp\kafka-logs

rmdir /s /q C:\tmp\zookeeper


Modify the listeners and advertised.listeners properties in your Kafka server.properties file
# The address the broker binds to for listening to client requests
listeners=PLAINTEXT://0.0.0.0:9092

# The address the broker advertises to clients
advertised.listeners=PLAINTEXT://hostname:9092
