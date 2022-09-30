package zhongchiedu.com.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GeneralEsMapper extends ElasticsearchRepository<Object, Long> {

}
