package life.picacg.community.community.schedule;

import life.picacg.community.community.cache.HotTagCache;
import life.picacg.community.community.dto.HotTagDTO;
import life.picacg.community.community.mapper.PublishMapper;
import life.picacg.community.community.model.Publish;
import life.picacg.community.community.model.PublishExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    PublishMapper publishMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
//    @Scheduled(cron = "0 0 1 * * *")
    public void hotTagScheduled() {
        int offset = 0;
        int limit = 20;
        log.info("hotTagScheduled start {}", new Date());
        List<Publish> list = new ArrayList<>();
        Map<String, HotTagDTO> priorities = new HashMap<>();

        while (offset == 0 || list.size() == limit) {
            list = publishMapper.selectByExampleWithRowbounds(new PublishExample(), new RowBounds(offset, limit));
            for (Publish publish : list) {
                String[] tags = StringUtils.split(publish.getTag(), ",");
                for (String tag : tags) {
                    Integer priority;
                    Integer viewCount;
                    Integer commentCount;
                    if(priorities.get(tag) != null){
                        //热门程度综合计算
                        priority = priorities.get(tag).getPriority();
                        //浏览数计算
                        viewCount = priorities.get(tag).getViewCount();
                        //评论数计算
                        commentCount = priorities.get(tag).getCommentCount();
                    }else {
                        priorities.put(tag,new HotTagDTO());
                        priorities.get(tag).setName(tag);
                        priority = null;
                        viewCount = null;
                        commentCount = null;
                    }

                    if(priority == null){
                        priorities.get(tag).setPriority(publish.getViewCount() + 5 * publish.getCommentCount());
                    }else{
                        priorities.get(tag).setPriority( priority +publish.getViewCount() + 5 * publish.getCommentCount());
                    }
                    if(viewCount == null){
                        priorities.get(tag).setViewCount(publish.getViewCount());
                    }else{
                        priorities.get(tag).setViewCount( viewCount +publish.getViewCount());
                    }
                    if(commentCount == null){
                        priorities.get(tag).setCommentCount(publish.getCommentCount());
                    }else{
                        priorities.get(tag).setCommentCount(commentCount +  publish.getCommentCount());
                    }
                    priorities.put(tag,priorities.get(tag));
                }
            }
            offset += limit;
        }

        hotTagCache.updateTags(priorities);
        log.info("hotTagScheduled stop {}", new Date());
    }

}
