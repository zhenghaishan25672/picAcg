package life.picacg.community.community.dto;

import lombok.Data;


@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;
    private Integer viewCount;
    private Integer commentCount;
    

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
