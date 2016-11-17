package cmcc.oa.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cmcc.oa.base.JsonResult;
import cmcc.oa.entity.ProcessInfo;
import cmcc.oa.entity.VisitingInfo;
import cmcc.oa.vo.MMS;
import cmcc.oa.vo.VisitingInfoVo;

public interface VisitingInfoService {
	/**
	 * 查询来访人员记录
	 */
	public JsonResult QueryVisitInfo(String id);
	
	/**
	 * 登记来访人员进入信息
	 */
	public JsonResult AddComVisitInfo(VisitingInfo visitingInfo);
	
	/**
	 * 登记来访人员出去信息
	 */
	public void AddOutVisitInfo(VisitingInfo visitingInfo);
	
	/**
	 * 流程表插入数据，返回流程信息id
	 */
	public JsonResult AddProcess(ProcessInfo processInfo);
	/**
	 *来访人员信息批量插入
	 */
	public JsonResult BatchAddVisitInfo(List<VisitingInfo> visitingInfos);
	
	/**
     * 多文件上传
     * @param request
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public void  multipleFilesUpload(HttpServletRequest request,HttpServletResponse responseng ) throws Exception;
    
    /**
     * 判断用户进入前台显示信息
     * @return
     */
    public VisitingInfoVo showVisitInfo(VisitingInfoVo visitingInfo);
    
	List<MMS> getUnsentVisitInfo();
}
