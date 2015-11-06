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

import com.ail.warmachine1.domain.enumeration.Parameter_usage_type;

import com.ail.warmachine1.domain.enumeration.Parameter_type;

/**
 * A Api_params.
 */
@Entity
@Table(name = "api_params")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "api_params")
public class Api_params implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "api_id", nullable = false)
    private String api_id;

    @NotNull
    @Column(name = "parameter", nullable = false)
    private String parameter;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "usage_type", nullable = false)
    private Parameter_usage_type usage_type;

    @Column(name = "default_value")
    private String default_value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Parameter_type type;

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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Parameter_usage_type getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(Parameter_usage_type usage_type) {
        this.usage_type = usage_type;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public Parameter_type getType() {
        return type;
    }

    public void setType(Parameter_type type) {
        this.type = type;
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

        Api_params api_params = (Api_params) o;

        if ( ! Objects.equals(id, api_params.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Api_params{" +
            "id=" + id +
            ", api_id='" + api_id + "'" +
            ", parameter='" + parameter + "'" +
            ", usage_type='" + usage_type + "'" +
            ", default_value='" + default_value + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
