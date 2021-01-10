package com.newlife.charge.controller;


import com.newlife.charge.common.IpUtils;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.dto.in.GeneralizeStationLogSaveIn;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.GeneralizeStationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * 推广建站记录noToken
 */
@PlatLogModule(moduleId = "/api/noToken/generalizeStation", moduleName = "推广建站记录noToken")
@Api(description = "推广建站记录不需要token")
@RestController
@RequestMapping("/api/noToken/generalizeStation")
public class GeneralizeStationLogNotokenController extends BaseController {

    //每天每个ip能够提交的次数
    private static final Integer ADD_FREQUENCY = 5;

    private Logger logger = LoggerFactory.getLogger(GeneralizeStationLogNotokenController.class);

    @Autowired
    private GeneralizeStationLogService stationLogService;

    /**
     * 添加建站推广人记录
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response insert(@RequestBody @Valid @ApiParam(required = true) GeneralizeStationLogSaveIn saveIn
            , BindingResult bindingResult, HttpServletRequest request) {

        throwBindingResultErrorMsg(bindingResult);

        //次数校验
        String ipAddr = IpUtils.getIpAddr(request);

        Integer count = 0;
        synchronized (this){
            String key = "generalizeStation_save_" + ipAddr;
            String value = RedisUtils.getRedisValue(key);
            if (StringUtils.isNotBlank(value)) {
                count = Integer.valueOf(value);
            }

            if (count >= ADD_FREQUENCY) {
                String msg = "提交次数已经达到限制(5次/天),请勿重复提交!";
                logger.info(msg + ipAddr);
                throw new BizException(msg);
            }
            count++;
            RedisUtils.setRedisValue(key, String.valueOf(count), 1, TimeUnit.DAYS);
        }


        try {

            stationLogService.insert(saveIn);

            return success().setMessage("保存成功");

        }catch (Exception e){
            logger.info("====> 建站推广人记录保存失败");
            throw new BizException(ERROR.INTERNAL_ERROR);
        }
    }


}
