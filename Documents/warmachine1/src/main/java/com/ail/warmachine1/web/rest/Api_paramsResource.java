package com.ail.warmachine1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ail.warmachine1.domain.Api_params;
import com.ail.warmachine1.repository.Api_paramsRepository;
import com.ail.warmachine1.repository.search.Api_paramsSearchRepository;
import com.ail.warmachine1.web.rest.util.HeaderUtil;
import com.ail.warmachine1.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Api_params.
 */
@RestController
@RequestMapping("/api")
public class Api_paramsResource {

    private final Logger log = LoggerFactory.getLogger(Api_paramsResource.class);

    @Inject
    private Api_paramsRepository api_paramsRepository;

    @Inject
    private Api_paramsSearchRepository api_paramsSearchRepository;

    /**
     * POST  /api_paramss -> Create a new api_params.
     */
    @RequestMapping(value = "/api_paramss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_params> createApi_params(@Valid @RequestBody Api_params api_params) throws URISyntaxException {
        log.debug("REST request to save Api_params : {}", api_params);
        if (api_params.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new api_params cannot already have an ID").body(null);
        }
        Api_params result = api_paramsRepository.save(api_params);
        api_paramsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/api_paramss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("api_params", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api_paramss -> Updates an existing api_params.
     */
    @RequestMapping(value = "/api_paramss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_params> updateApi_params(@Valid @RequestBody Api_params api_params) throws URISyntaxException {
        log.debug("REST request to update Api_params : {}", api_params);
        if (api_params.getId() == null) {
            return createApi_params(api_params);
        }
        Api_params result = api_paramsRepository.save(api_params);
        api_paramsSearchRepository.save(api_params);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("api_params", api_params.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api_paramss -> get all the api_paramss.
     */
    @RequestMapping(value = "/api_paramss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Api_params>> getAllApi_paramss(Pageable pageable)
        throws URISyntaxException {
        Page<Api_params> page = api_paramsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api_paramss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /api_paramss/:id -> get the "id" api_params.
     */
    @RequestMapping(value = "/api_paramss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_params> getApi_params(@PathVariable Long id) {
        log.debug("REST request to get Api_params : {}", id);
        return Optional.ofNullable(api_paramsRepository.findOne(id))
            .map(api_params -> new ResponseEntity<>(
                api_params,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /api_paramss/:id -> delete the "id" api_params.
     */
    @RequestMapping(value = "/api_paramss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApi_params(@PathVariable Long id) {
        log.debug("REST request to delete Api_params : {}", id);
        api_paramsRepository.delete(id);
        api_paramsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("api_params", id.toString())).build();
    }

    /**
     * SEARCH  /_search/api_paramss/:query -> search for the api_params corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/api_paramss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Api_params> searchApi_paramss(@PathVariable String query) {
        return StreamSupport
            .stream(api_paramsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
