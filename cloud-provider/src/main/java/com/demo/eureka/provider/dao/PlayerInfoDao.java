package com.demo.eureka.provider.dao;


import com.demo.eureka.provider.model.PlayerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * PlayerInfoDao
 *
 * @author 10905 2019/1/8
 * @version 1.0
 */
@Mapper
public interface PlayerInfoDao {


    List<PlayerInfo> getPlayerInfoByCondition(Map<String, Object> map);



}
