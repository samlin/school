package com.lxit.service;

import com.lxit.entity.Reship;

/**
 * Service接口 - 退货
 */

public interface ReshipService extends BaseService<Reship, String> {

    /**
     * 获取最后生成的退货编号
     * 
     * @return 退货编号
     */
    public String getLastReshipSn();

}