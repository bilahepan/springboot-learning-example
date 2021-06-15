package org.springboot.pulsar.service;

import org.springboot.pulsar.domain.City;

/**
 * 城市业务逻辑接口类
 *
 * Created by bysocket on 07/02/2017.
 */
public interface CityService {

    /**
     * 获取城市
     *
     */
    City getCityByName(String cityName);

    /**
     * 新增城市信息
     *
     */
    void saveCity(City city);

    /**
     * 更新城市信息
     *
     */
    void updateCityDescription(String cityName, String description);

}
