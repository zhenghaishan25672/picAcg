package life.picacg.community.community.mapper;

import life.picacg.community.community.model.Publish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface PublishMapper {
    @Insert("insert into PUBLISH(title,description,gmt_create,gmt_modified,creator,tag) values" +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Publish publish);

    @Select("select * from publish limit #{offset},#{size}")
    List<Publish> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from publish")
    Integer count ();
}
