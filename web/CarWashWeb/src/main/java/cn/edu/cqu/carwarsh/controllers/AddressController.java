package cn.edu.cqu.carwarsh.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.cqu.carwarsh.services.AddressService;
import cn.edu.cqu.carwarsh.services.CustomerService;
/**
 * 用户常用洗车地址的基本操作，包括添加、删除、修改、查看
 * @author liuji
 *
 */
@RestController
public class AddressController {
	/**
	 * 用于输出日志
	 */
	private static Logger logger = LoggerFactory
			.getLogger(CustomerController.class);
	@Autowired
	private AddressService addressService;
	@Autowired
	private CustomerService customerService;
}
