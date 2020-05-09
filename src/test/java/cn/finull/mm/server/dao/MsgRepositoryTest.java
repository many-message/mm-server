package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.Msg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-09 14:05
 */
@Slf4j
@SpringBootTest
class MsgRepositoryTest {

    @Autowired
    private MsgRepository msgRepository;

    @Test
    void signMsgBySendUserIdAndRecvUserId() {
        msgRepository.signMsgBySendUserIdAndRecvUserId(1L, 2L);
    }

    @Test
    void findAllMsg() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Msg> messages = msgRepository.findAllMsg(1L, 2L);

        log.info("===========================================================");
        messages.forEach(msg -> {
            log.info("msgId:{}, sendUserId:{}, recvUserId:{}, content:{}, sign:{}, createTime:{}",
                    msg.getMsgId(),
                    msg.getSendUserId(),
                    msg.getRecvUserId(),
                    msg.getMsgContent(),
                    msg.getSign(),
                    dateFormat.format(msg.getCreateTime()));
        });
        log.info("===========================================================");
    }
}