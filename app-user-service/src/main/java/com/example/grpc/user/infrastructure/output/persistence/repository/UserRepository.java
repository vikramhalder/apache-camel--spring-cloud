package com.example.grpc.user.infrastructure.output.persistence.repository;

import com.example.grpc.user.infrastructure.output.persistence.entity.User;
import com.example.grpc.user.infrastructure.output.persistence.entity.User_;
import com.example.grpc.user.infrastructure.output.persistence.specification.JpaSearchCriteria;
import com.example.grpc.user.infrastructure.output.persistence.specification.JpaSearchOperation;
import com.example.grpc.user.infrastructure.output.persistence.specification.JpaSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    default Page<User> filter(Pageable pageable, String name, String email) {
        final JpaSpecificationBuilder<User> specificationBuilder = new JpaSpecificationBuilder<>();

        if (StringUtils.hasLength(name)) {
            specificationBuilder.add(new JpaSearchCriteria(User_.NAME, JpaSearchOperation.MATCH, name));
        }
        if (StringUtils.hasLength(email)) {
            specificationBuilder.add(new JpaSearchCriteria(User_.EMAIL, JpaSearchOperation.EQUAL, email));
        }
        return findAll(specificationBuilder, pageable);
    }
}
