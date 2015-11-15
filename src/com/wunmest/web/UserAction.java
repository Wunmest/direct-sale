package com.wunmest.web;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wunmest.database.entity.User;
import com.wunmest.domain.XException;
import com.wunmest.domain.XResponse;
import com.wunmest.service.UserService;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserAction {

	@Resource
	private UserService userService;
	@Resource
	private User userServer;
	@Resource
	private XResponse xResponse;
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	@Transactional
	@ResponseBody
	public XResponse reg(User userClient, HttpServletRequest request){
		try {
			// 推荐人
			String referrerName = request.getParameter("referrerName");
			Long referrerTel = Long.valueOf(request.getParameter("referrerTel"));
			// 如果推荐人满足下面的条件, 那么就是初始会员, 初始会员的推荐人 uid 为 0.
			if("银溪源".equals(referrerName) && 18888888888l == referrerTel)
				userServer.setUid(0l);
			userServer.setRealname(referrerName);
			userServer.setTel(referrerTel);
			userClient.setReferrer(userServer);
			
			userService.reg(userClient, request);
		} catch (Exception e) {
			e.printStackTrace();
			xResponse.setReturnCode(0);
			if(e.getCause() instanceof SQLException){
				SQLException sqlException = (SQLException) e.getCause();
				int errorCode = sqlException.getErrorCode();
				String errorMsg = sqlException.getMessage();
				switch(errorCode){
				case 1062:
					Pattern pattern = Pattern.compile("('\\w+')$");
					Matcher matcher = pattern.matcher(errorMsg);
					if(matcher.find()){
						if("'username'".equals(matcher.group())){
							xResponse.setReturnMessage("用户名已存在");
						}else if ("'tel'".equals(matcher.group())){
							xResponse.setReturnMessage("该手机号码已经注册");
						}else if ("'uuid'".equals(matcher.group())){
							xResponse.setReturnMessage("该身份证号已经注册");
						}
					}
					break;
				default:
					xResponse.setReturnMessage("哎哟, 出问题了");
				}
			}else if(e instanceof XException){
				xResponse.setReturnMessage(((XException)e).getMsg());
			}else{
				xResponse.setReturnMessage("哎哟, 出问题了");
			}
		}
		return xResponse;
	}
	/*
	//根据真实姓名查询推荐人个数
	@RequestMapping("/referrer/{relaname}")
	@ResponseBody
	public XResponse selectByRealname(@PathVariable String realname){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userCount", userService.selectByRealname(realname).size());
		xResponse.setData(data);
		return xResponse;
	}
	*/
}
