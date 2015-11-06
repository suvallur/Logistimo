package com.ail.warmachine1.web.rest;

import com.ail.warmachine1.Application;
import com.ail.warmachine1.domain.Api_info;
import com.ail.warmachine1.repository.Api_infoRepository;
import com.ail.warmachine1.repository.search.Api_infoSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ail.warmachine1.domain.enumeration.Env;

/**
 * Test class for the Api_infoResource REST controller.
 *
 * @see Api_infoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Api_infoResourceTest {

    private static final String DEFAULT_API_ID = "AAAAA";
    private static final String UPDATED_API_ID = "BBBBB";
    private static final String DEFAULT_PROJECT = "AAAAA";
    private static final String UPDATED_PROJECT = "BBBBB";
    private static final String DEFAULT_METHOD = "AAAAA";
    private static final String UPDATED_METHOD = "BBBBB";
    private static final String DEFAULT_BASE_URL = "AAAAA";
    private static final String UPDATED_BASE_URL = "BBBBB";
    private static final String DEFAULT_FRAGMENT = "AAAAA";
    private static final String UPDATED_FRAGMENT = "BBBBB";


private static final Env DEFAULT_ENVIRONMENT = Env.QA;
    private static final Env UPDATED_ENVIRONMENT = Env.PROD;
    private static final String DEFAULT_REQ_BODY = "AAAAA";
    private static final String UPDATED_REQ_BODY = "BBBBB";

    @Inject
    private Api_infoRepository api_infoRepository;

    @Inject
    private Api_infoSearchRepository api_infoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApi_infoMockMvc;

    private Api_info api_info;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Api_infoResource api_infoResource = new Api_infoResource();
        ReflectionTestUtils.setField(api_infoResource, "api_infoRepository", api_infoRepository);
        ReflectionTestUtils.setField(api_infoResource, "api_infoSearchRepository", api_infoSearchRepository);
        this.restApi_infoMockMvc = MockMvcBuilders.standaloneSetup(api_infoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        api_info = new Api_info();
        api_info.setApi_id(DEFAULT_API_ID);
        api_info.setProject(DEFAULT_PROJECT);
        api_info.setMethod(DEFAULT_METHOD);
        api_info.setBase_url(DEFAULT_BASE_URL);
        api_info.setFragment(DEFAULT_FRAGMENT);
        api_info.setEnvironment(DEFAULT_ENVIRONMENT);
        api_info.setReq_body(DEFAULT_REQ_BODY);
    }

    @Test
    @Transactional
    public void createApi_info() throws Exception {
        int databaseSizeBeforeCreate = api_infoRepository.findAll().size();

        // Create the Api_info

        restApi_infoMockMvc.perform(post("/api/api_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_info)))
                .andExpect(status().isCreated());

        // Validate the Api_info in the database
        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeCreate + 1);
        Api_info testApi_info = api_infos.get(api_infos.size() - 1);
        assertThat(testApi_info.getApi_id()).isEqualTo(DEFAULT_API_ID);
        assertThat(testApi_info.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testApi_info.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testApi_info.getBase_url()).isEqualTo(DEFAULT_BASE_URL);
        assertThat(testApi_info.getFragment()).isEqualTo(DEFAULT_FRAGMENT);
        assertThat(testApi_info.getEnvironment()).isEqualTo(DEFAULT_ENVIRONMENT);
        assertThat(testApi_info.getReq_body()).isEqualTo(DEFAULT_REQ_BODY);
    }

    @Test
    @Transactional
    public void checkApi_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_infoRepository.findAll().size();
        // set the field null
        api_info.setApi_id(null);

        // Create the Api_info, which fails.

        restApi_infoMockMvc.perform(post("/api/api_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_info)))
                .andExpect(status().isBadRequest());

        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_infoRepository.findAll().size();
        // set the field null
        api_info.setProject(null);

        // Create the Api_info, which fails.

        restApi_infoMockMvc.perform(post("/api/api_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_info)))
                .andExpect(status().isBadRequest());

        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_infoRepository.findAll().size();
        // set the field null
        api_info.setMethod(null);

        // Create the Api_info, which fails.

        restApi_infoMockMvc.perform(post("/api/api_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_info)))
                .andExpect(status().isBadRequest());

        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBase_urlIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_infoRepository.findAll().size();
        // set the field null
        api_info.setBase_url(null);

        // Create the Api_info, which fails.

        restApi_infoMockMvc.perform(post("/api/api_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_info)))
                .andExpect(status().isBadRequest());

        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnvironmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_infoRepository.findAll().size();
        // set the field null
        api_info.setEnvironment(null);

        // Create the Api_info, which fails.

        restApi_infoMockMvc.perform(post("/api/api_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_info)))
                .andExpect(status().isBadRequest());

        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApi_infos() throws Exception {
        // Initialize the database
        api_infoRepository.saveAndFlush(api_info);

        // Get all the api_infos
        restApi_infoMockMvc.perform(get("/api/api_infos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(api_info.getId().intValue())))
                .andExpect(jsonPath("$.[*].api_id").value(hasItem(DEFAULT_API_ID.toString())))
                .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
                .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
                .andExpect(jsonPath("$.[*].base_url").value(hasItem(DEFAULT_BASE_URL.toString())))
                .andExpect(jsonPath("$.[*].fragment").value(hasItem(DEFAULT_FRAGMENT.toString())))
                .andExpect(jsonPath("$.[*].environment").value(hasItem(DEFAULT_ENVIRONMENT.toString())))
                .andExpect(jsonPath("$.[*].req_body").value(hasItem(DEFAULT_REQ_BODY.toString())));
    }

    @Test
    @Transactional
    public void getApi_info() throws Exception {
        // Initialize the database
        api_infoRepository.saveAndFlush(api_info);

        // Get the api_info
        restApi_infoMockMvc.perform(get("/api/api_infos/{id}", api_info.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(api_info.getId().intValue()))
            .andExpect(jsonPath("$.api_id").value(DEFAULT_API_ID.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.base_url").value(DEFAULT_BASE_URL.toString()))
            .andExpect(jsonPath("$.fragment").value(DEFAULT_FRAGMENT.toString()))
            .andExpect(jsonPath("$.environment").value(DEFAULT_ENVIRONMENT.toString()))
            .andExpect(jsonPath("$.req_body").value(DEFAULT_REQ_BODY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApi_info() throws Exception {
        // Get the api_info
        restApi_infoMockMvc.perform(get("/api/api_infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApi_info() throws Exception {
        // Initialize the database
        api_infoRepository.saveAndFlush(api_info);

		int databaseSizeBeforeUpdate = api_infoRepository.findAll().size();

        // Update the api_info
        api_info.setApi_id(UPDATED_API_ID);
        api_info.setProject(UPDATED_PROJECT);
        api_info.setMethod(UPDATED_METHOD);
        api_info.setBase_url(UPDATED_BASE_URL);
        api_info.setFragment(UPDATED_FRAGMENT);
        api_info.setEnvironment(UPDATED_ENVIRONMENT);
        api_info.setReq_body(UPDATED_REQ_BODY);

        restApi_infoMockMvc.perform(put("/api/api_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_info)))
                .andExpect(status().isOk());

        // Validate the Api_info in the database
        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeUpdate);
        Api_info testApi_info = api_infos.get(api_infos.size() - 1);
        assertThat(testApi_info.getApi_id()).isEqualTo(UPDATED_API_ID);
        assertThat(testApi_info.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testApi_info.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testApi_info.getBase_url()).isEqualTo(UPDATED_BASE_URL);
        assertThat(testApi_info.getFragment()).isEqualTo(UPDATED_FRAGMENT);
        assertThat(testApi_info.getEnvironment()).isEqualTo(UPDATED_ENVIRONMENT);
        assertThat(testApi_info.getReq_body()).isEqualTo(UPDATED_REQ_BODY);
    }

    @Test
    @Transactional
    public void deleteApi_info() throws Exception {
        // Initialize the database
        api_infoRepository.saveAndFlush(api_info);

		int databaseSizeBeforeDelete = api_infoRepository.findAll().size();

        // Get the api_info
        restApi_infoMockMvc.perform(delete("/api/api_infos/{id}", api_info.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Api_info> api_infos = api_infoRepository.findAll();
        assertThat(api_infos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
