package org.tm.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tm.repository.UserRepository;

import java.util.HashMap;


class AuthorizeServiceTest {

    @Mock private UserRepository userRepository;

    private AuthorizeService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AuthorizeService(userRepository);
    }

    @Test
    void loginSuccess() {

//        UserDTO userToLogin = new UserDTO();
//        userToLogin.setUsername("tanmeng");
//        userToLogin.setPassword("123456");
//
//        UserDTO userInDB = new UserDTO();
//        userInDB.setUsername("tanmeng");
//        userInDB.setPassword("123456");

//        BDDMockito.given(userRepository.selectUserBy(userToLogin.getUsername()));
//                .willReturn(userInDB);

        //HashMap actual = underTest.login(userToLogin);

        var expected = new HashMap<>();
        expected.put("code",0);
        expected.put("msg","登录成功");

        //Assertions.assertThat(actual).isEqualTo(expected);
    }
    @Test
    void loginFailed() {

//        UserDTO userToLogin = new UserDTO();
//        userToLogin.setUsername("tanmeng");
//        userToLogin.setPassword("123457");
//
//        UserDTO userInDB = new UserDTO();
//        userInDB.setUsername("tanmeng");
//        userInDB.setPassword("123456");

//        BDDMockito.given(userRepository.selectUserBy(userToLogin.getUsername()));
//                .willReturn(userInDB);

        //HashMap actual = underTest.login(userToLogin);

        var expected = new HashMap<>();
        expected.put("code",-1);
        expected.put("msg","用户名或密码错误");

        //Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test()
    void registerFailed() {

//        UserDTO userToRegister = new UserDTO();
//        userToRegister.setUsername("liry");
//        userToRegister.setPassword("liry923");

        //given
        BDDMockito.given(userRepository.selectUserBy("liry"));
//                .willReturn(userToRegister);
        //when
        //then
//        org.junit.jupiter.api.Assertions.assertThrows(UserExistsException.class,
//                ()->underTest.register(userToRegister));
    }

    @Test()
    void registerSuccess() {

//        UserDTO userToRegister = new UserDTO();
//        userToRegister.setUsername("liry");
//        userToRegister.setPassword("liry923");

        //given
        BDDMockito.given(userRepository.selectUserBy("liry"))
                .willReturn(null);
        //when
//        underTest.register(userToRegister);

        //then
        BDDMockito.given(userRepository.selectUserBy("liry"));
//                .willReturn(userToRegister);
//        org.junit.jupiter.api.Assertions.assertThrows(UserExistsException.class,
//                ()->underTest.register(userToRegister));
    }
}