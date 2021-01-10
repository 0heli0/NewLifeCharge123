/**
 * Author: Fuq
 * Date:   2018/5/24 9:13
 * Descripition:定时任务日志AOP类
 */
package com.newlife.charge.service.aop;

import com.newlife.charge.core.domain.TaskErrorLog;
import com.newlife.charge.dao.TaskErrorLogMapper;
import com.newlife.charge.service.task.NewLifeChargeTaskScheduler;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Aspect
public class TaskLogAspect {
    //TODO:推荐使用service
    @Autowired
    private TaskErrorLogMapper taskErrorLogMapper;

    private Logger logger = LoggerFactory.getLogger(NewLifeChargeTaskScheduler.class);

    @Pointcut(" execution(* com.newlife.charge.service.task.NewLifeChargeTaskScheduler.*(..)) ")
    public void pointcutName() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint
     */
    @Before("pointcutName()")
    public void begin(JoinPoint joinPoint) {
//        System.out.println("=====前置通知开始=====");
        logger.info("=====前置通知开始=====");
        succeeLog(joinPoint);
        logger.info("每天触发 桩站定时结算 Task: %s, Current time: " + LocalDateTime.now() + "\n");
    }

    /**
     * 异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "pointcutName()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.info("=====异常通知=====");
        failLog(joinPoint, e);
    }

    // 成功
    private void succeeLog(JoinPoint joinPoint) {
        TaskErrorLog taskErrorLog = new TaskErrorLog();
        taskErrorLog.setActionStatus("成功");
        taskErrorLog.setExtendParam("");
        executeLog(joinPoint, taskErrorLog);
        logger.info("=====成功=====");
    }

    // 失败
    private void failLog(JoinPoint joinPoint, Throwable e) {
        TaskErrorLog taskErrorLog = new TaskErrorLog();
        taskErrorLog.setActionStatus("失败");
        taskErrorLog.setExtendParam("");
        taskErrorLog.setRemark(e.getMessage());
        executeLog(joinPoint, taskErrorLog);
        logger.info("=====失败=====");

    }

    // 执行记录日志
    private void executeLog(JoinPoint joinPoint, TaskErrorLog taskErrorLog) {
        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        taskErrorLog.setActionType("桩站定时结算");
        if(StringUtils.isBlank(taskErrorLog.getExtendParam())){
            taskErrorLog.setExtendParam("");
        }
        taskErrorLog.setClassPath(targetName + methodName);
        taskErrorLog.setCreateTime(new Date());
        if(StringUtils.isBlank(taskErrorLog.getRemark())){
            taskErrorLog.setRemark("");
        }
        this.taskErrorLogMapper.insert(taskErrorLog);
        logger.info("=====执行记录日志：桩站定时结算=====");

    }
}
