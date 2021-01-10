/**
 * Author: zhengyou
 * Descripition:充电桩历史数据
 */
package com.newlife.charge.core.dto.in;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PileUploadHistoryDataIn {


    private PileHistoryDataIn pileHistoryDataIn;


//    /**
//     * 10个时段充电电量和充电金额
//     * 0x0117 80字节
//     * 0-3字节表阶段1充电电量，4-7字节表示阶段1充电金额
//     */
//    private List<StageDataIn> stageDataInList ;



}
