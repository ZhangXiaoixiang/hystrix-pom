package com.demo.eureka.provider;

import com.demo.eureka.provider.dao.PlayerInfoDao;
import com.demo.eureka.provider.model.PlayerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaProviderApplicationTests {
@Autowired
private PlayerInfoDao playerInfoDao;

    @Test
    public void contextLoads() {
        List<PlayerInfo> playerInfoByCondition = playerInfoDao.getPlayerInfoByCondition(null);
        System.out.println(playerInfoByCondition);
    }

}

