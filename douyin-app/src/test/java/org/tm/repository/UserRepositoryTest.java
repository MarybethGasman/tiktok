package org.tm.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@MybatisTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldSelectUserByUsername() {
        //given
//        UserDTO userDto = new UserDTO();
//        userDto.setUsername("Jamila");
//        userDto.setPassword("123456");
//        userDto.setUserId(2L);

        //underTest.insertUser(userDto);

        //when
//        UserDTO expected = underTest.selectUserBy("Jamila");

        //then
//        Assertions.assertThat(expected.getUsername()).isEqualTo(userDto.getUsername());
    }
}