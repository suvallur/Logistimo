package com.ail.warmachine1.repository;

import com.ail.warmachine1.domain.Api_info;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Api_info entity.
 */
public interface Api_infoRepository extends JpaRepository<Api_info,Long> {

}
