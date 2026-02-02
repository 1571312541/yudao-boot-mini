package cn.iocoder.yudao.module.dim.dal.dataobject.room;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 住客 DO
 */
@TableName("dim_room_guest")
@KeySequence("dim_room_guest_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomGuestDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 住客姓名
     */
    private String guestName;

    /**
     * 证件类型（0-身份证 1-军官证 2-护照 3-其他）
     */
    private Integer idType;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 单位/部门
     */
    private String deptName;

    /**
     * 入住日期
     */
    private LocalDateTime checkInDate;

    /**
     * 预计离店日期
     */
    private LocalDateTime expectedCheckOutDate;

    /**
     * 实际离店日期
     */
    private LocalDateTime actualCheckOutDate;

    /**
     * 住宿状态（0-在住 1-已退房 2-预约中 3-已取消）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 主住客ID（同住人用，主住客为null）
     */
    private Long primaryGuestId;

    /**
     * 是否主住客（true=主住客，false=同住人）
     */
    private Boolean isPrimary;

}
