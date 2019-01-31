package com.demo.eureka.provider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * player_info
 * @author 
 */
@Data//getset方法
@ToString//toString方法
@NoArgsConstructor//无参
@AllArgsConstructor//全参
public class PlayerInfo implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 球员编码
     */
    private Integer playerId;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 中文名称
     */
    private String nameCh;

    /**
     * 球队名称
     */
    private String teamId;

    /**
     * 球员角色
     */
    private String playerRole;

    /**
     * 身高
     */
    private String height;

    /**
     * 体重
     */
    private String weight;

    /**
     * 球龄
     */
    private Integer bollYear;

    /**
     * 球员国籍
     */
    private String nationality;

    /**
     * 球员场均数据
     */
    private Integer playerRecordId;


    public PlayerInfo(Integer playerId, String nameEn, String nameCh, String teamId, String playerRole, String height, String weight, Integer bollYear, String nationality, Integer playerRecordId) {
        this.playerId = playerId;
        this.nameEn = nameEn;
        this.nameCh = nameCh;
        this.teamId = teamId;
        this.playerRole = playerRole;
        this.height = height;
        this.weight = weight;
        this.bollYear = bollYear;
        this.nationality = nationality;
        this.playerRecordId = playerRecordId;
    }
}