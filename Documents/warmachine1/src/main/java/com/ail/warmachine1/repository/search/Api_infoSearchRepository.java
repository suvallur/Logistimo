package com.ail.warmachine1.repository.search;

import com.ail.warmachine1.domain.Api_info;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Api_info entity.
 */
public interface Api_infoSearchRepository extends ElasticsearchRepository<Api_info, Long> {
}
