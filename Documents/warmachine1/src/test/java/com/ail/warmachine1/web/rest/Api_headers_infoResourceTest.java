package com.ail.warmachine1.web.rest;

import com.ail.warmachine1.Application;
import com.ail.warmachine1.domain.Api_headers_info;
import com.ail.warmachine1.repository.Api_headers_infoRepository;
import com.ail.warmachine1.repository.search.Api_headers_infoSearchRepository;

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


/**
 * Test class for the Api_headers_infoResource REST controller.
 *
 * @see Api_headers_infoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Api_headers_infoResourceTest {

    private static final String DEFAULT_API_ID = "AAAAA";
    private static final String UPDATED_API_ID = "BBBBB";
    private static final String DEFAULT_HEADERS = "AAAAA";
    private static final String UPDATED_HEADERS = "BBBBB";
    private static final String DEFAULT_DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBB";

    @Inject
    private Api_headers_infoRepository api_headers_infoRepository;

    @Inject
    private Api_headers_infoSearchRepository api_headers_infoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApi_headers_infoMockMvc;

    private Api_headers_info api_headers_info;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Api_headers_infoResource api_headers_infoResource = new Api_headers_infoResource();
        ReflectionTestUtils.setField(api_headers_infoResource, "api_headers_infoRepository", api_headers_infoRepository);
        ReflectionTestUtils.setField(api_headers_infoResource, "api_headers_infoSearchRepository", api_headers_infoSearchRepository);
        this.restApi_headers_infoMockMvc = MockMvcBuilders.standaloneSetup(api_headers_infoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        api_headers_info = new Api_headers_info();
        api_headers_info.setApi_id(DEFAULT_API_ID);
        api_headers_info.setHeaders(DEFAULT_HEADERS);
        api_headers_info.setDefault_value(DEFAULT_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createApi_headers_info() throws Exception {
        int databaseSizeBeforeCreate = api_headers_infoRepository.findAll().size();

        // Create the Api_headers_info

        restApi_headers_infoMockMvc.perform(post("/api/api_headers_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_headers_info)))
                .andExpect(status().isCreated());

        // Validate the Api_headers_info in the database
        List<Api_headers_info> api_headers_infos = api_headers_infoRepository.findAll();
        assertThat(api_headers_infos).hasSize(databaseSizeBeforeCreate + 1);
        Api_headers_info testApi_headers_info = api_headers_infos.get(api_headers_infos.size() - 1);
        assertThat(testApi_headers_info.getApi_id()).isEqualTo(DEFAULT_API_ID);
        assertThat(testApi_headers_info.getHeaders()).isEqualTo(DEFAULT_HEADERS);
        assertThat(testApi_headers_info.getDefault_value()).isEqualTo(DEFAULT_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkApi_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_headers_infoRepository.findAll().size();
        // set the field null
        api_headers_info.setApi_id(null);

        // Create the Api_headers_info, which fails.

        restApi_headers_infoMockMvc.perform(post("/api/api_headers_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_headers_info)))
                .andExpect(status().isBadRequest());

        List<Api_headers_info> api_headers_infos = api_headers_infoRepository.findAll();
        assertThat(api_headers_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeadersIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_headers_infoRepository.findAll().size();
        // set the field null
        api_headers_info.setHeaders(null);

        // Create the Api_headers_info, which fails.

        restApi_headers_infoMockMvc.perform(post("/api/api_headers_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_headers_info)))
                .andExpect(status().isBadRequest());

        List<Api_headers_info> api_headers_infos = api_headers_infoRepository.findAll();
        assertThat(api_headers_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefault_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_headers_infoRepository.findAll().size();
        // set the field null
        api_headers_info.setDefault_value(null);

        // Create the Api_headers_info, which fails.

        restApi_headers_infoMockMvc.perform(post("/api/api_headers_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_headers_info)))
                .andExpect(status().isBadRequest());

        List<Api_headers_info> api_headers_infos = api_headers_infoRepository.findAll();
        assertThat(api_headers_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApi_headers_infos() throws Exception {
        // Initialize the database
        api_headers_infoRepository.saveAndFlush(api_headers_info);

        // Get all the api_headers_infos
        restApi_headers_infoMockMvc.perform(get("/api/api_headers_infos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(api_headers_info.getId().intValue())))
                .andExpect(jsonPath("$.[*].api_id").value(hasItem(DEFAULT_API_ID.toString())))
                .andExpect(jsonPath("$.[*].headers").value(hasItem(DEFAULT_HEADERS.toString())))
                .andExpect(jsonPath("$.[*].default_value").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getApi_headers_info() throws Exception {
        // Initialize the database
        api_headers_infoRepository.saveAndFlush(api_headers_info);

        // Get the api_headers_info
        restApi_headers_infoMockMvc.perform(get("/api/api_headers_infos/{id}", api_headers_info.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(api_headers_info.getId().intValue()))
            .andExpect(jsonPath("$.api_id").value(DEFAULT_API_ID.toString()))
            .andExpect(jsonPath("$.headers").value(DEFAULT_HEADERS.toString()))
            .andExpect(jsonPath("$.default_value").value(DEFAULT_DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApi_headers_info() throws Exception {
        // Get the api_headers_info
        restApi_headers_infoMockMvc.perform(get("/api/api_headers_infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApi_headers_info() throws Exception {
        // Initialize the database
        api_headers_infoRepository.saveAndFlush(api_headers_info);

		int databaseSizeBeforeUpdate = api_headers_infoRepository.findAll().size();

        // Update the api_headers_info
        api_headers_info.setApi_id(UPDATED_API_ID);
        api_headers_info.setHeaders(UPDATED_HEADERS);
        api_headers_info.setDefault_value(UPDATED_DEFAULT_VALUE);

        restApi_headers_infoMockMvc.perform(put("/api/api_headers_infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_headers_info)))
                .andExpect(status().isOk());

        // Validate the Api_headers_info in the database
        List<Api_headers_info> api_headers_infos = api_headers_infoRepository.findAll();
        assertThat(api_headers_infos).hasSize(databaseSizeBeforeUpdate);
        Api_headers_info testApi_headers_info = api_headers_infos.get(api_headers_infos.size() - 1);
        assertThat(testApi_headers_info.getApi_id()).isEqualTo(UPDATED_API_ID);
        assertThat(testApi_headers_info.getHeaders()).isEqualTo(UPDATED_HEADERS);
        assertThat(testApi_headers_info.getDefault_value()).isEqualTo(UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void deleteApi_headers_info() throws Exception {
        // Initialize the database
        api_headers_infoRepository.saveAndFlush(api_headers_info);

		int databaseSizeBeforeDelete = api_headers_infoRepository.findAll().size();

        // Get the api_headers_info
        restApi_headers_infoMockMvc.perform(delete("/api/api_headers_infos/{id}", api_headers_info.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Api_headers_info> api_headers_infos = api_headers_infoRepository.findAll();
        assertThat(api_headers_infos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
