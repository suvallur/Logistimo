package com.ail.warmachine1.web.rest;

import com.ail.warmachine1.Application;
import com.ail.warmachine1.domain.Api_params;
import com.ail.warmachine1.repository.Api_paramsRepository;
import com.ail.warmachine1.repository.search.Api_paramsSearchRepository;

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

import com.ail.warmachine1.domain.enumeration.Parameter_usage_type;
import com.ail.warmachine1.domain.enumeration.Parameter_type;

/**
 * Test class for the Api_paramsResource REST controller.
 *
 * @see Api_paramsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Api_paramsResourceTest {

    private static final String DEFAULT_API_ID = "AAAAA";
    private static final String UPDATED_API_ID = "BBBBB";
    private static final String DEFAULT_PARAMETER = "AAAAA";
    private static final String UPDATED_PARAMETER = "BBBBB";


private static final Parameter_usage_type DEFAULT_USAGE_TYPE = Parameter_usage_type.OPTIONAL;
    private static final Parameter_usage_type UPDATED_USAGE_TYPE = Parameter_usage_type.MANDATORY;
    private static final String DEFAULT_DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBB";


private static final Parameter_type DEFAULT_TYPE = Parameter_type.STR;
    private static final Parameter_type UPDATED_TYPE = Parameter_type.NUM;

    @Inject
    private Api_paramsRepository api_paramsRepository;

    @Inject
    private Api_paramsSearchRepository api_paramsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApi_paramsMockMvc;

    private Api_params api_params;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Api_paramsResource api_paramsResource = new Api_paramsResource();
        ReflectionTestUtils.setField(api_paramsResource, "api_paramsRepository", api_paramsRepository);
        ReflectionTestUtils.setField(api_paramsResource, "api_paramsSearchRepository", api_paramsSearchRepository);
        this.restApi_paramsMockMvc = MockMvcBuilders.standaloneSetup(api_paramsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        api_params = new Api_params();
        api_params.setApi_id(DEFAULT_API_ID);
        api_params.setParameter(DEFAULT_PARAMETER);
        api_params.setUsage_type(DEFAULT_USAGE_TYPE);
        api_params.setDefault_value(DEFAULT_DEFAULT_VALUE);
        api_params.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createApi_params() throws Exception {
        int databaseSizeBeforeCreate = api_paramsRepository.findAll().size();

        // Create the Api_params

        restApi_paramsMockMvc.perform(post("/api/api_paramss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_params)))
                .andExpect(status().isCreated());

        // Validate the Api_params in the database
        List<Api_params> api_paramss = api_paramsRepository.findAll();
        assertThat(api_paramss).hasSize(databaseSizeBeforeCreate + 1);
        Api_params testApi_params = api_paramss.get(api_paramss.size() - 1);
        assertThat(testApi_params.getApi_id()).isEqualTo(DEFAULT_API_ID);
        assertThat(testApi_params.getParameter()).isEqualTo(DEFAULT_PARAMETER);
        assertThat(testApi_params.getUsage_type()).isEqualTo(DEFAULT_USAGE_TYPE);
        assertThat(testApi_params.getDefault_value()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testApi_params.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkApi_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_paramsRepository.findAll().size();
        // set the field null
        api_params.setApi_id(null);

        // Create the Api_params, which fails.

        restApi_paramsMockMvc.perform(post("/api/api_paramss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_params)))
                .andExpect(status().isBadRequest());

        List<Api_params> api_paramss = api_paramsRepository.findAll();
        assertThat(api_paramss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParameterIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_paramsRepository.findAll().size();
        // set the field null
        api_params.setParameter(null);

        // Create the Api_params, which fails.

        restApi_paramsMockMvc.perform(post("/api/api_paramss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_params)))
                .andExpect(status().isBadRequest());

        List<Api_params> api_paramss = api_paramsRepository.findAll();
        assertThat(api_paramss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsage_typeIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_paramsRepository.findAll().size();
        // set the field null
        api_params.setUsage_type(null);

        // Create the Api_params, which fails.

        restApi_paramsMockMvc.perform(post("/api/api_paramss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_params)))
                .andExpect(status().isBadRequest());

        List<Api_params> api_paramss = api_paramsRepository.findAll();
        assertThat(api_paramss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = api_paramsRepository.findAll().size();
        // set the field null
        api_params.setType(null);

        // Create the Api_params, which fails.

        restApi_paramsMockMvc.perform(post("/api/api_paramss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_params)))
                .andExpect(status().isBadRequest());

        List<Api_params> api_paramss = api_paramsRepository.findAll();
        assertThat(api_paramss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApi_paramss() throws Exception {
        // Initialize the database
        api_paramsRepository.saveAndFlush(api_params);

        // Get all the api_paramss
        restApi_paramsMockMvc.perform(get("/api/api_paramss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(api_params.getId().intValue())))
                .andExpect(jsonPath("$.[*].api_id").value(hasItem(DEFAULT_API_ID.toString())))
                .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER.toString())))
                .andExpect(jsonPath("$.[*].usage_type").value(hasItem(DEFAULT_USAGE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].default_value").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getApi_params() throws Exception {
        // Initialize the database
        api_paramsRepository.saveAndFlush(api_params);

        // Get the api_params
        restApi_paramsMockMvc.perform(get("/api/api_paramss/{id}", api_params.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(api_params.getId().intValue()))
            .andExpect(jsonPath("$.api_id").value(DEFAULT_API_ID.toString()))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER.toString()))
            .andExpect(jsonPath("$.usage_type").value(DEFAULT_USAGE_TYPE.toString()))
            .andExpect(jsonPath("$.default_value").value(DEFAULT_DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApi_params() throws Exception {
        // Get the api_params
        restApi_paramsMockMvc.perform(get("/api/api_paramss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApi_params() throws Exception {
        // Initialize the database
        api_paramsRepository.saveAndFlush(api_params);

		int databaseSizeBeforeUpdate = api_paramsRepository.findAll().size();

        // Update the api_params
        api_params.setApi_id(UPDATED_API_ID);
        api_params.setParameter(UPDATED_PARAMETER);
        api_params.setUsage_type(UPDATED_USAGE_TYPE);
        api_params.setDefault_value(UPDATED_DEFAULT_VALUE);
        api_params.setType(UPDATED_TYPE);

        restApi_paramsMockMvc.perform(put("/api/api_paramss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(api_params)))
                .andExpect(status().isOk());

        // Validate the Api_params in the database
        List<Api_params> api_paramss = api_paramsRepository.findAll();
        assertThat(api_paramss).hasSize(databaseSizeBeforeUpdate);
        Api_params testApi_params = api_paramss.get(api_paramss.size() - 1);
        assertThat(testApi_params.getApi_id()).isEqualTo(UPDATED_API_ID);
        assertThat(testApi_params.getParameter()).isEqualTo(UPDATED_PARAMETER);
        assertThat(testApi_params.getUsage_type()).isEqualTo(UPDATED_USAGE_TYPE);
        assertThat(testApi_params.getDefault_value()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testApi_params.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteApi_params() throws Exception {
        // Initialize the database
        api_paramsRepository.saveAndFlush(api_params);

		int databaseSizeBeforeDelete = api_paramsRepository.findAll().size();

        // Get the api_params
        restApi_paramsMockMvc.perform(delete("/api/api_paramss/{id}", api_params.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Api_params> api_paramss = api_paramsRepository.findAll();
        assertThat(api_paramss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
