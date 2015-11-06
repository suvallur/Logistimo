package com.ail.warmachine1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.ail.warmachine1.domain.enumeration.Env;

/**
 * A Api_info.
 */
@Entity
@Table(name = "api_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "api_info")
public class Api_info implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "api_id", nullable = false)
    private String api_id;

    @NotNull
    @Column(name = "project", nullable = false)
    private String project;

    @NotNull
    @Column(name = "method", nullable = false)
    private String method;

    @NotNull
    @Column(name = "base_url", nullable = false)
    private String base_url;

    @Column(name = "fragment")
    private String fragment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "environment", nullable = false)
    private Env environment;

    @Column(name = "req_body")
    private String req_body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApi_id() {
        return api_id;
    }

    public void setApi_id(String api_id) {
        this.api_id = api_id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public Env getEnvironment() {
        return environment;
    }

    public void setEnvironment(Env environment) {
        this.environment = environment;
    }

    public String getReq_body() {
        return req_body;
    }

    public void setReq_body(String req_body) {
        this.req_body = req_body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Api_info api_info = (Api_info) o;

        if ( ! Objects.equals(id, api_info.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Api_info{" +
            "id=" + id +
            ", api_id='" + api_id + "'" +
            ", project='" + project + "'" +
            ", method='" + method + "'" +
            ", base_url='" + base_url + "'" +
            ", fragment='" + fragment + "'" +
            ", environment='" + environment + "'" +
            ", req_body='" + req_body + "'" +
            '}';
    }
}
