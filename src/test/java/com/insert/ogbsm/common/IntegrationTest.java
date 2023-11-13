package com.insert.ogbsm.common;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class IntegrationTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void DB테이블의_값을_정리한다() {
        databaseCleaner.clear();
    }
}
