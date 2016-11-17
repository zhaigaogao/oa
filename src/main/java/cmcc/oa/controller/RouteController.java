package cmcc.oa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cmcc.oa.base.BaseController;

/**
 *	静态路由
 * @author renlinggao
 * @Date 2016年10月21日
 */
@Controller
public class RouteController extends BaseController{
	// login
	@RequestMapping("/scancode")
	public String login(HttpServletRequest request) {
		return "scancode/scancode";
	}
}
