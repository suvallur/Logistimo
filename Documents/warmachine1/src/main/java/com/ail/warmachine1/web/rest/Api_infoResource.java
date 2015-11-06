package com.ail.warmachine1.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ail.warmachine1.domain.Api_info;
import com.ail.warmachine1.repository.Api_infoRepository;
import com.ail.warmachine1.repository.search.Api_infoSearchRepository;
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
 * REST controller for managing Api_info.
 */
@RestController
@RequestMapping("/api")
public class Api_infoResource {

    private final Logger log = LoggerFactory.getLogger(Api_infoResource.class);

    @Inject
    private Api_infoRepository api_infoRepository;

    @Inject
    private Api_infoSearchRepository api_infoSearchRepository;

    /**
     * POST  /api_infos -> Create a new api_info.
     */
    @RequestMapping(value = "/api_infos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_info> createApi_info(@Valid @RequestBody Api_info api_info) throws URISyntaxException {
        log.debug("REST request to save Api_info : {}", api_info);
        if (api_info.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new api_info cannot already have an ID").body(null);
        }
        Api_info result = api_infoRepository.save(api_info);
        api_infoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/api_infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("api_info", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api_infos -> Updates an existing api_info.
     */
    @RequestMapping(value = "/api_infos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_info> updateApi_info(@Valid @RequestBody Api_info api_info) throws URISyntaxException {
        log.debug("REST request to update Api_info : {}", api_info);
        if (api_info.getId() == null) {
            return createApi_info(api_info);
        }
        Api_info result = api_infoRepository.save(api_info);
        api_infoSearchRepository.save(api_info);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("api_info", api_info.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api_infos -> get all the api_infos.
     */
    @RequestMapping(value = "/api_infos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Api_info>> getAllApi_infos(Pageable pageable)
        throws URISyntaxException {
        Page<Api_info> page = api_infoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api_infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /api_infos/:id -> get the "id" api_info.
     */
    @RequestMapping(value = "/api_infos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Api_info> getApi_info(@PathVariable Long id) {
        log.debug("REST request to get Api_info : {}", id);
        return Optional.ofNullable(api_infoRepository.findOne(id))
            .map(api_info -> new ResponseEntity<>(
                api_info,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /api_infos/:id -> delete the "id" api_info.
     */
    @RequestMapping(value = "/api_infos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApi_info(@PathVariable Long id) {
        log.debug("REST request to delete Api_info : {}", id);
        api_infoRepository.delete(id);
        api_infoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("api_info", id.toString())).build();
    }

    /**
     * SEARCH  /_search/api_infos/:query -> search for the api_info corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/api_infos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Api_info> searchApi_infos(@PathVariable String query) {
        return StreamSupport
            .stream(api_infoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
