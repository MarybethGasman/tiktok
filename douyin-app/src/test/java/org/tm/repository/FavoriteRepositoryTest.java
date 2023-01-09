package org.tm.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.tm.po.FavoritePO;

import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@MybatisTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void insertFail() {
        FavoritePO favoritePO = new FavoritePO();
        favoritePO.setUserId(1L);
        favoritePO.setVideoId(2L);

        Assertions.assertThrows(DuplicateKeyException.class,
                ()->underTest.insert(favoritePO));
    }
}