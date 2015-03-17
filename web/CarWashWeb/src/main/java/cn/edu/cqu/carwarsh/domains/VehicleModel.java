package cn.edu.cqu.carwarsh.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
/**
 * 车辆型号
 * @author liuji
 *
 */
@Entity
public class VehicleModel {

		/**
		 * 逻辑主键，自增长
		 */
		@Id
		@GeneratedValue
		private Long id;
		/**
		 * 车辆品牌关联
		 */
		@ManyToOne(optional=false)
		private VehicleBrand brand;
		/**
		 * 车型名，例如福克斯
		 */
		@Column(nullable=false,length=50)
		private String name;
		/**
		 * 车型名拼音首字母
		 */
		@Column(nullable=false,length=1)
		private String pinyin;
		
}
