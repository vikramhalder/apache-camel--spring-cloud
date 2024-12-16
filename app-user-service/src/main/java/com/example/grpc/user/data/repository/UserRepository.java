package com.example.grpc.user.data.repository;

import com.example.grpc.user.data.entity.User;
import com.example.grpc.user.data.entity.User_;
import com.example.grpc.user.data.specification.JpaSearchCriteria;
import com.example.grpc.user.data.specification.JpaSearchOperation;
import com.example.grpc.user.data.specification.JpaSpecificationBuilder;
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
