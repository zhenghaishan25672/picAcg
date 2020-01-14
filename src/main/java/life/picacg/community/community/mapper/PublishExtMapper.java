package life.picacg.community.community.mapper;

import life.picacg.community.community.model.Publish;
import life.picacg.community.community.model.PublishExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PublishExtMapper {
    int incView(Publish record);
    int incCommentCount(Publish record);
    List<Publish> selectRelated(Publish publish);
}