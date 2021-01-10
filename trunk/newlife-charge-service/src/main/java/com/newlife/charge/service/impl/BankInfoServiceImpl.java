package com.newlife.charge.service.impl;

import com.newlife.charge.core.dto.out.BankInfoOut;
import com.newlife.charge.dao.BankInfoMapper;
import com.newlife.charge.service.BankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BankInfoServiceImpl implements BankInfoService {

    @Autowired
    private BankInfoMapper bankInfoMapper;

    @Override
    public List<BankInfoOut> list() {
        return this.bankInfoMapper.page();
    }
}
