package com.ail.warmachine1.repository.search;

import com.ail.warmachine1.domain.Api_params;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Api_params entity.
 */
public interface Api_paramsSearchRepository extends ElasticsearchRepository<Api_params, Long> {
}
