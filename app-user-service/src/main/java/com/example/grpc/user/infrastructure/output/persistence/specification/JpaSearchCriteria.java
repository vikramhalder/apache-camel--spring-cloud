package com.example.grpc.user.infrastructure.output.persistence.specification;

import lombok.Getter;

@Getter
public class JpaSearchCriteria {
    private Object value;
    private Object[] values;
    private final String key;
    private final JpaSearchOperation operation;

    public JpaSearchCriteria(String key, JpaSearchOperation operation, Object value) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public JpaSearchCriteria(String key, JpaSearchOperation operation, Object value1, Object value2) {
        this.key = key;
        this.values = new Object[]{value1, value2};
        this.operation = operation;
    }
}
