package org.seckill.service;

import org.junit.Test;
import org.seckill.BaseTest;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SeckillServiceTest extends BaseTest{

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        System.out.println(seckill);
    }

    @Test
    public void testGetSeckillList() throws Exception {
        System.out.println(seckillService.getSeckillList());
    }

    /**
     * 集成测试：秒杀完整流程，可重复执行
     */
    @Test
    public void testSeckillLogic() {

        long id = 1001;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        LOG.info("exposer={}",exposer);
        if (exposer.isExposed()) {

            long phone = 15821739225L;
            String md5 = exposer.getMd5();

            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                LOG.info("result={}",seckillExecution);
            } catch (RepeatKillException e) {
                LOG.error(e.getMessage());
            } catch (SeckillCloseException e) {
                LOG.error(e.getMessage());
            }

        } else {
            LOG.warn("秒杀未开始：{}",exposer.toString());
        }

    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        LOG.info("exposer={}",exposer.toString());
//     Exposer{exposed=true, md5='be3d9cdd642d64f8ed97eb05e93b9628', seckillId=1000, now=0, start=0, end=0}
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1000;
        long phone = 15821739223L;

        String md5 = "be3d9cdd642d64f8ed97eb05e93b9628";

        SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);

        LOG.info("seckillExecution={}",seckillExecution);

    }

    @Test
    public void testExecuteSeckillProcedure() {
        long seckillId = 1001L;
        long phone = 12311111111L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService
                    .executeSeckillProcedure(seckillId, phone, md5);
            LOG.info(seckillExecution.getStateInfo());
        }
    }

}