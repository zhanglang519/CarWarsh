package cn.edu.cqu.carwarsh.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 用户常用车辆信息
 * @author liuji
 *
 */
@Entity
public class Vehicle {
	/**
	 * 逻辑主键，自增长
	 */
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 与用户关联
	 */
	@ManyToOne(optional=false)
	private Customer customer;
	/**
	 * 与车辆型号关联
	 */
	@ManyToOne(optional=false)
	private VehicleModel vehicleModel;
	
	/**
	 * 
	 * 车牌号
	 */
	@Column(nullable=false,length=20)
	private String licenseNumber;
	/**
	 * 
	 * 车辆颜色
	 */
	@Column(nullable=true,length=20)
	private String color;
	
	//TODO 添加其他属性
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public VehicleModel getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(VehicleModel vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
}
