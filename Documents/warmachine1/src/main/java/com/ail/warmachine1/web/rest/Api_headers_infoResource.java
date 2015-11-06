package com.ail.warmachine1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ail.warmachine1.domain.Api_headers_info;
import com.ail.warmachine1.repository.Api_headers_infoRepository;
import com.ail.warmachine1.repository.search.Api_headers_infoSearchRepository;
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
 * REST controller for managing Api_headers_info.
 */
@RestController
@RequestMapping("/api")
public class Api_headers_infoResource {

    private final Logger log = LoggerFactory.getLogger(Api_headers_infoResource.class);

    @Inject
    private Api_headers_infoRepository api_headers_infoRepository;

    @Inject
    private Api_headers_infoSearchRepository api_headers_infoSearchRepository;

    /**
     * POST  /api_headers_infos -> Create a new api_headers_info.
     */
    @RequestMapping(value = "/api_headers_infos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_headers_info> createApi_headers_info(@Valid @RequestBody Api_headers_info api_headers_info) throws URISyntaxException {
        log.debug("REST request to save Api_headers_info : {}", api_headers_info);
        if (api_headers_info.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new api_headers_info cannot already have an ID").body(null);
        }
        Api_headers_info result = api_headers_infoRepository.save(api_headers_info);
        api_headers_infoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/api_headers_infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("api_headers_info", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api_headers_infos -> Updates an existing api_headers_info.
     */
    @RequestMapping(value = "/api_headers_infos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_headers_info> updateApi_headers_info(@Valid @RequestBody Api_headers_info api_headers_info) throws URISyntaxException {
        log.debug("REST request to update Api_headers_info : {}", api_headers_info);
        if (api_headers_info.getId() == null) {
            return createApi_headers_info(api_headers_info);
        }
        Api_headers_info result = api_headers_infoRepository.save(api_headers_info);
        api_headers_infoSearchRepository.save(api_headers_info);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("api_headers_info", api_headers_info.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api_headers_infos -> get all the api_headers_infos.
     */
    @RequestMapping(value = "/api_headers_infos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Api_headers_info>> getAllApi_headers_infos(Pageable pageable)
        throws URISyntaxException {
        Page<Api_headers_info> page = api_headers_infoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api_headers_infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /api_headers_infos/:id -> get the "id" api_headers_info.
     */
    @RequestMapping(value = "/api_headers_infos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_headers_info> getApi_headers_info(@PathVariable Long id) {
        log.debug("REST request to get Api_headers_info : {}", id);
        return Optional.ofNullable(api_headers_infoRepository.findOne(id))
            .map(api_headers_info -> new ResponseEntity<>(
                api_headers_info,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /api_headers_infos/:id -> delete the "id" api_headers_info.
     */
    @RequestMapping(value = "/api_headers_infos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApi_headers_info(@PathVariable Long id) {
        log.debug("REST request to delete Api_headers_info : {}", id);
        api_headers_infoRepository.delete(id);
        api_headers_infoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("api_headers_info", id.toString())).build();
    }

    /**
     * SEARCH  /_search/api_headers_infos/:query -> search for the api_headers_info corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/api_headers_infos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Api_headers_info> searchApi_headers_infos(@PathVariable String query) {
        return StreamSupport
            .stream(api_headers_infoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
