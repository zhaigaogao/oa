package cmcc.oa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cmcc.oa.base.BaseController;
import cmcc.oa.base.JsonResult;
import cmcc.oa.entity.ProcessInfo;
import cmcc.oa.entity.VisitingInfo;
import cmcc.oa.service.VisitingInfoService;
import cmcc.oa.vo.MMS;
import cmcc.oa.vo.MMSs;



@Controller
@RequestMapping("/visitingInfo")
public class VisitingInfoController extends BaseController {
	
	/**
	 * @author zhaieryuan
	 */
	private Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private VisitingInfoService visitingInfoService;
	
	/**
	 * @Method 根据来访人员二维码id查询来访人员信息
	 * @param id
	 * @return
	 */
	@RequestMapping("queryVisitInfo")
	@ResponseBody
	public JsonResult queryVisitInfo(@RequestParam(value="id",required=true) String id){
		JsonResult result=new JsonResult();
		if(id !=null && id!=""){
			result=visitingInfoService.QueryVisitInfo(id);
		}else {
			result.setMessage("参数不能为空");
			result.setSuccess(false);
		}
		return result;
	}
	/**
	 * @Method 人员进入添加记录
	 * @author zhaieryuan
	 * @throws ParseException 
	 */
	@RequestMapping("addComVisitInfo")
	@ResponseBody
	public JsonResult addComVisitInfo(VisitingInfo visitingInfo) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JsonResult result=new JsonResult();
		if(visitingInfo != null){
			visitingInfo.setUserStatus(1);
			visitingInfo.setEntryTime(sdf.parse(sdf.format(new Date())));
			result=visitingInfoService.AddComVisitInfo(visitingInfo);
		}else {
			result.setMessage("参数不能为空");
			result.setSuccess(false);
		}
		return result;
	}
	/**
	 * @author zhaieryuan
	 * @method 人员外出登记录入
	 * @param  VisitingInfo
	 * @return JsonResult
	 * @throws ParseException 
	 */
	@RequestMapping("addOutVisitInfo")
	@ResponseBody
	public JsonResult addOutVisitInfo(@RequestParam(value="id")String id) throws ParseException {
		JsonResult result=new JsonResult();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		VisitingInfo visitingInfo=new VisitingInfo();
		visitingInfo.setId(Long.parseLong(id));
		visitingInfo.setUserStatus(2);
		visitingInfo.setOutTime(sdf.parse(sdf.format(new Date())));
		visitingInfoService.AddOutVisitInfo(visitingInfo);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * @method 流程添加并返回流程id
	 * @param  ProcessInfo
	 * @return id
	 */
	@RequestMapping("addProcessInfo")
	@ResponseBody
	public JsonResult addProcessInfo(ProcessInfo processInfo){
		JsonResult result=new JsonResult();
		if(processInfo != null){
			result=visitingInfoService.AddProcess(processInfo);
		}else {
			result.setMessage("参数不能为空");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @method 人员进入批量插入数据和图片
	 * @param  VisitingInfo
	 * @return 
	 */
	@RequestMapping("batchAddComVisitInfo")
	@ResponseBody
	public JsonResult batchAddComVisitInfo(@RequestParam(name="jasonArray") String jasonArray,
			 HttpServletRequest request,HttpServletResponse response){
		JsonResult result=new JsonResult();
		List<VisitingInfo> list = new ArrayList<VisitingInfo>();  
        list = JSONObject.parseArray(jasonArray, VisitingInfo.class);
        try {
			visitingInfoService.multipleFilesUpload(request, response);
			result=visitingInfoService.BatchAddVisitInfo(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("附件保存失败");
		}
		return result;
	}
	
	@RequestMapping("getUnsentVisitInfo")
	@ResponseBody
	public JsonResult getUnsentVisitInfo() {
		JsonResult result = new JsonResult(true, null, null);
		List<MMS> visitInfo = visitingInfoService.getUnsentVisitInfo();
		if (visitInfo.isEmpty()) {
			// result.setSuccess(false);
			result.setMessage("目前暂无待发送信息");
		}
		MMSs mmss = new MMSs();
		mmss.setListMMS(visitInfo);
		result.setModel(mmss);
		return result;
	}
}
