package cn.edu.cqu.carwarsh.controllers.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.cqu.carwarsh.services.CustomerService;
import cn.edu.cqu.carwarsh.vos.JSONResult;
import cn.edu.cqu.carwarsh.vos.SMSCodes;
/**
 * 客户信息管理服务端接口。
 * @RestController标注表示，本类的所有方法均以json的方式返回数据
 * @author liuji
 *
 */
@RestController
public class CustomerController {
	/**
	 * 用于输出日志
	 */
	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	/**
	 * 存储随机生成的4位注册验证码
	 */
	private static SMSCodes registerCodes=new SMSCodes();
	/**
	 * 存储随机生成的4位忘记密码验证码
	 */
	private static SMSCodes forgetCodes=new SMSCodes();
	/**
	 * 短信服务提供商发送短信的URL接口地址
	 */
	private static  String SMS_URL;
	/**
	 * 短信服务的用户名
	 */
	private static  String SMS_USER;
	/**
	 * 短信服务的密码
	 */
	private static  String SMS_PWD;
	static{
		//初始化静态变量
		logger.debug("开始初始化");
		String dir=System.getenv("CAR_WARSH_WEB_CONFIG");
		logger.debug("CAR_WARSH_WEB_CONFIG的值是{}",dir);
		Properties p=new Properties();
		try {
			p.load(new FileInputStream(new File(dir,"sms.properties")));
			SMS_URL=(String) p.getProperty("SMS_URL");
			SMS_USER=(String)p.getProperty("SMS_USER");
			SMS_PWD=(String)p.getProperty("SMS_PWD");
			logger.debug("SMS_URL={},SMS_USER={},SMS_PWD={}",new Object[]{SMS_URL,SMS_USER,SMS_PWD});
		} catch (IOException e) {
			e.printStackTrace();
		}
		//启动一个线程定期清空registerCodes和forgetCodes中过期的验证码（此处使用jdk8的语法）
		new Thread(()->{
			while(true)
			{
				logger.debug("开始清除registerCodes中过期的验证码");
				registerCodes.removeInValid();
				logger.debug("结束清除registerCodes中过期的验证码");
				logger.debug("开始清除forgetCodes中过期的验证码");
				forgetCodes.removeInValid();
				logger.debug("结束清除forgetCodes中过期的验证码");
				try {
					//每2小时清空一次
					Thread.sleep(7200000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	/**
	 * 操作Customer表的对象
	 */
	@Autowired
	private CustomerService customerService;
	/**
	 * 请求生成4位注册验证码，并以短信的方式发送给用户
	 * 接口映射到/client/customer/requestRegisterCode.do
	 * @param mobile 手机号码
	 * @return true表示验证码生成成功，false表示生成失败
	 */
	@RequestMapping(value="/client/customer/requestRegisterCode.do")
	public JSONResult requesRegisterCode(String mobile)
	{
		//在registerCodes中查找是否有验证码，若有判断其是否在1分钟之前创建，若是1分钟内创建的，返回false
		//生成验证码，将其设置到registerCodes中
		//将验证码发送到用户的手机，返回true
		//若上述逻辑执行异常返回false
		JSONResult result=new JSONResult();
		result.setMsg("验证码生成成功");
		result.setState(true);
		return result;
	}
	/**
	 * 用户注册
	 * 接口映射到/client/customer/register.do
	 * @param mobile	手机号码
	 * @param code	验证码
	 * @param pwd	密码
	 * @return	返回“注册成功”表示注册成功，其他信息表示注册失败（其他信息包括“手机号已注册”、“验证码过期或不存在”等）。
	 */
	@RequestMapping(value="/client/customer/register.do")
	public JSONResult register(String mobile,String code,String pwd)
	{
		JSONResult result=new JSONResult();
		result.setMsg("注册成功");
		result.setState(true);
		return result;
	}
	/**
	 * 用户登录
	 * 接口映射到/client/customer/login.do
	 * @param mobile 手机号码
	 * @param pwd	密码
	 * @return	true表示登录成功，false表示登录失败
	 */
	@RequestMapping(value="/client/customer/login.do")
	public JSONResult login(String mobile,String pwd)
	{
		
		JSONResult result=new JSONResult();
		if(customerService.isValid(mobile, pwd))
		{
			result.setMsg("登录成功");
			result.setState(true);
		}else
		{
			result.setMsg("登录失败");
			result.setState(false);
		}
		return result;
	}
	/**
	 * 修改密码
	 * 接口映射到/client/customer/changePwd.do
	 * @param mobile 手机号码
	 * @param pwd	密码
	 * @param newPwd	新密码
	 * @return	true表示修改成功，false表示修改失败
	 */
	@RequestMapping(value="/client/customer/changePwd.do")
	public JSONResult changePwd(String mobile,String pwd,String newPwd)
	{
		JSONResult result=new JSONResult();
		result.setMsg("密码修改成功");
		result.setState(true);
		return result;
	}
	/**
	 * 请求生成4位忘记密码验证码，并以短信的方式发送给用户
	 * 接口映射到/client/customer/requestForgetCode.do
	 * @param mobile 手机号码
	 * @return true表示验证码生成成功，false表示生成失败
	 */
	@RequestMapping(value="/client/customer/requestForgetCode.do")
	public JSONResult requestForgetCode(String mobile)
	{
		//在forgetCodes中查找是否有验证码，若有判断其是否在1分钟之前创建，若是1分钟内创建的，返回false
		//生成验证码，将其设置到forgetCodes中
		//将验证码发送到用户的手机，返回true
		//若上述逻辑执行异常返回false
		JSONResult result=new JSONResult();
		result.setMsg("验证码生成成功");
		result.setState(true);
		return result;
	}
	/**
	 * 重置用户密码
	 * @param mobile	手机号
	 * @param code	短信验证码
	 * @param newPwd	新密码
	 * @return	true表示修改成功，false表示修改失败
	 */
	@RequestMapping(value="/client/customer/resetPwd.do")
	public JSONResult resetPwd(String mobile,String code,String newPwd)
	{
		JSONResult result=new JSONResult();
		result.setMsg("重置密码成功");
		result.setState(true);
		return result;
	}
}
