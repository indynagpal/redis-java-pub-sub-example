/*
from: https://codedestine.com/redis-jedis-pub-sub-java/
*/

package io.straker;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import static java.lang.System.out;

public class ChannelSubscribe{

    public static void main(String[] args) {
        Jedis jedis = null;
        try{
            jedis = new Jedis();

            JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    out.println("Channel " + channel + " has sent a message: " + message);
                    if(channel.equals("C1")){
                     unsubscribe("C1");
                    }
                }

                @Override
                public void onSubscribe(String channel, int subscribedChannels) {
                    out.println("Client is subscribed to channel: " + channel);
                    out.println("Client is subscribed to " + subscribedChannels + " no. of channels");
                }

                @Override
                public void onUnsubscribe(String channel, int subscribedChannels) {
                    out.println("Client subscribed from channel: " + channel);
                    out.println("Client is subscribed to " + subscribedChannels + " no. of channels");
                }
            };

            jedis.subscribe(jedisPubSub, "C1", "C2");

        } catch(Exception e){
            out.println("Exception: " + e.getMessage());
        } finally{
            if(jedis !=null){
                jedis.close();
            }
        }

    }
}
