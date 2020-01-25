package life.picacg.community.community.dto;

import lombok.Data;

@Data
public class ContributeQueryDTO {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
}
