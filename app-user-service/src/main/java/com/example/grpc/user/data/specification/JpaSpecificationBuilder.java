package com.example.grpc.user.data.specification;

import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JpaSpecificationBuilder<T> implements Specification<T> {
    private final List<JpaSearchCriteria> list = new ArrayList<>();

    public void add(JpaSearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    @NonNull
    public Predicate toPredicate(@Nullable Root<T> root, @Nullable CriteriaQuery<?> query, @Nullable CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();
        assert root != null;
        assert query != null;
        assert builder != null;
        for (JpaSearchCriteria criteria : list) {
            if (criteria.getOperation().equals(JpaSearchOperation.BETWEEN)) {
                if (criteria.getValues()[0] instanceof Date) {
                    predicates.add(builder.between(root.get(criteria.getKey()), (Date) criteria.getValues()[0], (Date) criteria.getValues()[1]));
                } else if (criteria.getValues()[0] instanceof LocalDateTime) {
                    predicates.add(builder.between(root.get(criteria.getKey()), (LocalDateTime) criteria.getValues()[0], (LocalDateTime) criteria.getValues()[1]));
                } else if (criteria.getValues()[0] instanceof OffsetDateTime) {
                    predicates.add(builder.between(root.get(criteria.getKey()), (OffsetDateTime) criteria.getValues()[0], (OffsetDateTime) criteria.getValues()[1]));
                }
            } else if (criteria.getOperation().equals(JpaSearchOperation.GREATER_THAN)) {
                if (criteria.getValue() instanceof Date date) {
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof LocalDateTime date) {
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof OffsetDateTime date) {
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), date));
                } else {
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), String.valueOf(criteria.getValue())));
                }
            } else if (criteria.getOperation().equals(JpaSearchOperation.LESS_THAN)) {
                if (criteria.getValue() instanceof Date date) {
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof LocalDateTime date) {
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof OffsetDateTime date) {
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), date));
                } else {
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), String.valueOf(criteria.getValue())));
                }
            } else if (criteria.getOperation().equals(JpaSearchOperation.GREATER_THAN_EQUAL)) {
                if (criteria.getValue() instanceof Date date) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof LocalDateTime date) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof OffsetDateTime date) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), date));
                } else {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), String.valueOf(criteria.getValue())));
                }
            } else if (criteria.getOperation().equals(JpaSearchOperation.LESS_THAN_EQUAL)) {
                if (criteria.getValue() instanceof Date date) {
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof LocalDateTime date) {
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), date));
                } else if (criteria.getValue() instanceof OffsetDateTime date) {
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), date));
                } else {
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), String.valueOf(criteria.getValue())));
                }
            } else if (criteria.getOperation().equals(JpaSearchOperation.NOT_EQUAL)) {
                predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(JpaSearchOperation.EQUAL)) {
                predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(JpaSearchOperation.MATCH)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue() + "%"));
            } else if (criteria.getOperation().equals(JpaSearchOperation.MATCH_END)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue() + "%"));
            } else if (criteria.getOperation().equals(JpaSearchOperation.MATCH_START)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue()));
            } else if (criteria.getOperation().equals(JpaSearchOperation.IN)) {
                predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
            } else if (criteria.getOperation().equals(JpaSearchOperation.NOT_IN)) {
                predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
            } else {
                predicates.add(builder.isNotNull(root.get(criteria.getKey())));
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
