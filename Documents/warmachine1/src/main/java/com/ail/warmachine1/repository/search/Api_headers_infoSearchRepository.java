package com.ail.warmachine1.repository.search;

import com.ail.warmachine1.domain.Api_headers_info;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Api_headers_info entity.
 */
public interface Api_headers_infoSearchRepository extends ElasticsearchRepository<Api_headers_info, Long> {
}
