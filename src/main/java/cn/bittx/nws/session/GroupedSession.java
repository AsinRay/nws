package cn.bittx.nws.session;


import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class GroupedSession {
    private static final ConcurrentMap<String, ChannelGroup> groupChannels = PlatformDependent.newConcurrentHashMap();



    /**
     *
     * @param group     must be validated.
     * @param channel   new channel
     * @return
     */
    public static boolean add(String group,Channel channel){
        ChannelGroup cg = groupChannels.get(group);
        if(cg != null){
            cg.add(channel);
            return true;
        }

        cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        cg.add(channel);
        groupChannels.put(group,cg);
        return true;
    }

    /**
     * group must validate
     * @param group         must be validated.
     * @param message
     */
    public static void send(String group,String message){
        ChannelGroup cg = groupChannels.get(group);
        if(cg != null){
            cg.write(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
        }
    }

    public static int getGroupSize(){
        groupChannels.keySet().forEach(System.out::println);
        return groupChannels.size();
    }

    public Map<String,Integer> getGroupCount(){
        Map<String,Integer> rtn = new HashMap<>();
        groupChannels.keySet().forEach(o->{
            rtn.put(o,groupChannels.get(o).size());
        });
        return rtn;
    }
}
