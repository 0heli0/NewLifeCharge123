/**
 * Author: zhengyou
 * Date:   2018/12/11 16:03
 * Descripition:
 */
package com.newlife.charge.service.impl;

import com.newlife.charge.core.dto.out.AreaOut;
import com.newlife.charge.dao.AreaMapper;
import com.newlife.charge.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;


    @Override
    public List<AreaOut> list(Integer parentId) {

        return this.areaMapper.page(parentId);

    }

    volatile private static int nextPrintWho = 1;


    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        Thread threadA = new Thread() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (nextPrintWho != 1) {
                        conditionA.await();
                    }
                    System.out.println("ThreadA");
                    nextPrintWho = 2;
                    conditionB.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            ;
        };

        Thread threadB = new Thread() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (nextPrintWho != 2) {
                        conditionB.await();
                    }
                    System.out.println("ThreadB ");
                    nextPrintWho = 3;
                    conditionC.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            ;
        };

        Thread threadC = new Thread() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (nextPrintWho != 3) {
                        conditionC.await();
                    }
                    System.out.println("ThreadC ");
                    nextPrintWho = 1;
                    conditionA.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

            ;
        };

        /*Thread[] aArray = new Thread[5];
        Thread[] bArray = new Thread[5];
        Thread[] cArray = new Thread[5];*/
        for (int i = 0; i < 5; i++) {
            Thread a = new Thread(threadA);
            Thread b = new Thread(threadB);
            Thread c = new Thread(threadC);
            a.start();
            b.start();
            c.start();
        }
    }
}
