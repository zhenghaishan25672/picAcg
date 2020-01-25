package life.picacg.community.community.cache;

import life.picacg.community.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private Map<String,HotTagDTO> hots = new HashMap<>();

    public void updateTags(Map<String, HotTagDTO> tags) {
        int max = 5;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
        Map<String, Integer> tags1 = new HashMap<>();

        tags.forEach((name, hotTag) -> {
            tags1.put(name,hotTag.getPriority());
        });

        tags1.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max) {
                priorityQueue.add(hotTagDTO);
            } else {
                //拿到最小的元素
                HotTagDTO minHot = priorityQueue.peek();
                //当标签的priority大于最小热点就插入
                if (hotTagDTO.compareTo(minHot) > 0) {
                    //移出队列最后一个元素
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        HotTagDTO poll = priorityQueue.peek();
        while (poll != null) {
            HotTagDTO finalPoll = poll;
            tags.forEach((name, hotTag) -> {
                if(finalPoll.getName().equals(name)) {
                    hots.put(name, hotTag);
                }
            });
            poll = priorityQueue.poll();

        }

    }
}
