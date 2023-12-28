package com.insert.ogbsm.common;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseCleaner implements InitializingBean {

    private final List<String> tableNames = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void findDatabaseTableNames() {
        List<Object[]> tableInfos = entityManager.createNativeQuery("SHOW TABLES").getResultList();
        for (Object[] tableInfo : tableInfos) {
            String tableName = (String) tableInfo[0];
            tableNames.add(tableName);
        }
    }

    // 테이블을 초기화한다
    private void truncate() {
        entityManager.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS %d", 0)).executeUpdate();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();
        }
        entityManager.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS %d", 1)).executeUpdate();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        findDatabaseTableNames();
    }

    @Transactional
    public void clear() {
        entityManager.clear();
        truncate();
    }
}