package com.ail.warmachine1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Api_headers_info.
 */
@Entity
@Table(name = "api_headers_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "api_headers_info")
public class Api_headers_info implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "api_id", nullable = false)
    private String api_id;

    @NotNull
    @Column(name = "headers", nullable = false)
    private String headers;

    @NotNull
    @Column(name = "default_value", nullable = false)
    private String default_value;

    @ManyToOne
    private Api_info api_info;

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

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public Api_info getApi_info() {
        return api_info;
    }

    public void setApi_info(Api_info api_info) {
        this.api_info = api_info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Api_headers_info api_headers_info = (Api_headers_info) o;

        if ( ! Objects.equals(id, api_headers_info.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Api_headers_info{" +
            "id=" + id +
            ", api_id='" + api_id + "'" +
            ", headers='" + headers + "'" +
            ", default_value='" + default_value + "'" +
            '}';
    }
}
