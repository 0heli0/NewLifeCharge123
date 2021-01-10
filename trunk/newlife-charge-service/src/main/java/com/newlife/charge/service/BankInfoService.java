package com.newlife.charge.service;


import com.newlife.charge.core.dto.out.BankInfoOut;

import java.util.List;

/**
 * 银行卡 service
 */
public interface BankInfoService {

    /**
     * 银行卡列表
     *
     * @return
     */
    List<BankInfoOut> list();

}
