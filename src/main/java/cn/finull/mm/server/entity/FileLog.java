package cn.finull.mm.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-11 15:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file_log")
public class FileLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "create_time", nullable = false)
    private Date createTime;
}
