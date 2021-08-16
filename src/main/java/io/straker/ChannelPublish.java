/*
from: https://codedestine.com/redis-jedis-pub-sub-java/
 */

package io.straker;

import redis.clients.jedis.Jedis;
import static java.lang.System.out;

public class ChannelPublish{

    private static final String JEDIS_HOST = "localhost";
    private static final int JEDIS_PORT = 6379;

    public static void main(String[] args) {
        Jedis jedis = null;

        try{
            jedis = new Jedis(JEDIS_HOST, JEDIS_PORT);

            sendMessage(jedis, "C1", "First message to channel");
            sendMessage(jedis, "C2", "First message to channel");
            sendMessage(jedis, "C1", "Second message to channel");
            sendMessage(jedis, "C2", "Second message to channel");

        } catch (Exception e) {
            out.println("Exception: " + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    private static void sendMessage(Jedis jedis, String channel, String message) {

        jedis.publish(channel, message);
        out.println("Channel: " + channel + " | Message: " + message);

    }

}
