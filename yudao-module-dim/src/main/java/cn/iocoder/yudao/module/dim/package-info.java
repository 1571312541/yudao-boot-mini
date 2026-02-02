/**
 * dim 模块，涿州综合管理系统业务模块。
 * 包含以下子模块：
 * <ul>
 *     <li>room - 住宿管理：楼栋、楼层、房间、入住</li>
 *     <li>dining - 餐饮管理：就餐记录、报餐登记、餐饮结算</li>
 *     <li>material - 物资管理：物资分类、物资信息、出入库、月度汇总</li>
 *     <li>consumable - 耗材管理：耗材分类、耗材信息、出入库</li>
 *     <li>asset - 资产管理：资产分类、资产信息、持有、外借</li>
 *     <li>visitor - 访客管理：访客信息、来访日志</li>
 *     <li>fire - 消防设备：消防设备登记与检查</li>
 *     <li>vehicle - 公务车辆：车辆管理、用车记录</li>
 * </ul>
 *
 * <p>1. Controller URL：以 /dim/ 开头，避免和其它 Module 冲突</p>
 * <p>2. DataObject 表名：以 dim_ 开头，方便在数据库中区分</p>
 */
package cn.iocoder.yudao.module.dim;
