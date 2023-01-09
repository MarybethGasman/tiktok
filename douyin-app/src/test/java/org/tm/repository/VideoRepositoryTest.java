package org.tm.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.tm.dto.VideoDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("dev")
@MybatisTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VideoRepositoryTest {

    @Autowired
    private VideoRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void selectVideoListByVideoIdList() {
    }
}