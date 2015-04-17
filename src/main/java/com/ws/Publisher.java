package com.ws;

import com.rabbitmq.client.*;

/**
 * Created by Akronys on 22/02/2015.
 */
public class Publisher {

    private static final String EXCHANGE_NAME = "logs";

    public  void publishScore(String message) throws java.io.IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
