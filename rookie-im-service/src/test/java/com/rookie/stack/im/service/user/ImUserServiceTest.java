package com.rookie.stack.im.service.user;

import com.rookie.stack.im.dao.user.ImUserDataDao;
import com.rookie.stack.im.domain.entity.user.ImUserData;
import com.rookie.stack.im.domain.vo.req.user.ImportUserReq;
import com.rookie.stack.im.domain.vo.resp.user.ImportUserResp;
import com.rookie.stack.im.service.user.impl.ImUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Name：ImUserServiceTest
 * Author：eumenides
 * Created on: 2024/12/18
 * Description:
 */
@SpringBootTest
public class ImUserServiceTest {

    @Mock
    private ImUserDataDao imUserDataDao; // Mocking DAO

    @InjectMocks
    private ImUserServiceImpl imUserService; // Injecting mocked DAO into service

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }
    @Test
    public void testImportUsers_Success() {
        // Given: mock the data and the behavior of DAO
        ImportUserReq importUserReq = new ImportUserReq();
        ImUserData user1 = new ImUserData();
        user1.setUserId("user1");
        user1.setNickName("User One");

        ImUserData user2 = new ImUserData();
        user2.setUserId("user2");
        user2.setNickName("User Two");

        importUserReq.setUserData(List.of(user1, user2));

        when(imUserDataDao.save(any(ImUserData.class))).thenReturn(true); // Mocking save() method to always return true

        // When: calling the importUsers method
        ImportUserResp response = imUserService.importUsers(importUserReq);

        // Then: verify that both users were added successfully
        assertNotNull(response);
        assertEquals(2, response.getSuccessUsers().size());
        assertTrue(response.getSuccessUsers().contains("user1"));
        assertTrue(response.getSuccessUsers().contains("user2"));
        assertTrue(response.getFailUsers().isEmpty());
    }
}
