/*
from: https://codedestine.com/redis-jedis-pub-sub-java/
 */
package io.straker;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class PatternSubscribe {

    private static final String JEDIS_HOST = "localhost";
    private static final int JEDIS_PORT = 6379;

    public static void main(String[] args) {

        try (Jedis jedis = new Jedis(JEDIS_HOST, JEDIS_PORT)) {

            JedisPubSub jedisPubSub = new JedisPubSub() {

                @Override
                public void onPMessage(String pattern, String channel, String message) {
                    System.out.println("Channel " + channel + " has sent a message: " + message + " on pattern " + pattern);
                    if (pattern.equals("C*")) {
                        /* Unsubscribe from pattern C* after first message is received. */
                        super.punsubscribe(pattern);
                    }
                }

                @Override
                public void onPUnsubscribe(String pattern, int subscribedChannels) {
                    System.out.println("Client is Unsubscribed from pattern: " + pattern);
                    System.out.println("Client is Subscribed to " + subscribedChannels + " no. of patterns");
                }

                @Override
                public void onPSubscribe(String pattern, int subscribedChannels) {
                    System.out.println("Client is Subscribed to pattern: " + pattern);
                    System.out.println("Client is Subscribed to " + subscribedChannels + " no. of patterns");
                }
            };

            jedis.psubscribe(jedisPubSub, "C*", "D?");

        } catch (Exception e) {

            System.out.println("Exception: " + e.getMessage());

        }

    }
}
