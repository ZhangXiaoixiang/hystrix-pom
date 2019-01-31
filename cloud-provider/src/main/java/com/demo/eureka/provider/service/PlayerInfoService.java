package com.demo.eureka.provider.service;



import com.demo.eureka.provider.model.PlayerInfo;
import com.demo.eureka.provider.dao.PlayerInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * PlayerInfoService
 * 球员信息服务
 *
 * @author 10905 2019/1/8
 * @version 1.0
 */
@Service
//@Transactional//事务
public class PlayerInfoService implements PlayerInfoDao {
    @Autowired
    private PlayerInfoDao playerInfoDao;


    @Override
    public List<PlayerInfo> getPlayerInfoByCondition(Map<String, Object> map) {

        System.out.println("---------------循环----------------");
       System.out.println("服务层被调用了,数据为==>"+playerInfoDao.getPlayerInfoByCondition(map));
        return playerInfoDao.getPlayerInfoByCondition(map);

//        PlayerInfo playerInfo=new PlayerInfo();
//        playerInfo.setNameCh("死循环");
//        playerInfo.setHeight("1.78米");
//        List<PlayerInfo> list=new ArrayList<>();
//        list.add(playerInfo);
//        return list;
    }

}
