package life.picacg.community.community.mapper;

import life.picacg.community.community.dto.ContributeQueryDTO;
import life.picacg.community.community.model.Publish;

import java.util.List;

public interface PublishExtMapper {
    int incView(Publish record);
    int incCommentCount(Publish record);
    List<Publish> selectRelated(Publish publish);

    Integer countBySearch(ContributeQueryDTO contributeQueryDTO);

    List<Publish> selectBySearch(ContributeQueryDTO contributeQueryDTO);
}