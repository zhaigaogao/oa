package oa;


import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cmcc.oa.base.JsonResult;
import cmcc.oa.entity.ProcessInfo;
import cmcc.oa.service.VisitingInfoService;

/**
*@author zhaieryuan
*@Date 2016/10/21
*/
public class VisitInfoTest extends BaseTest{
	
	@Resource
	private VisitingInfoService visitingInfoService;
	
	@Test
	@Transactional // 标明此方法需使用事务
	@Rollback(true) // 标明使用完此方法后事务不回滚,true时为回滚
	public void testQueryList() {
		JsonResult result=new JsonResult();
		String codeId="1111";
		result=visitingInfoService.QueryVisitInfo(codeId);
		System.out.println(result.getModel().toString());
	}
	
	@Test
	@Transactional // 标明此方法需使用事务
	@Rollback(true) // 标明使用完此方法后事务不回滚,true时为回滚
	public void testAddProcess() {
		JsonResult result=new JsonResult();
		ProcessInfo processInfo=new ProcessInfo();
		processInfo.setCompanyId("0011");
		processInfo.setCreateUserAllOrgName("0012");
		processInfo.setCreateUserId("0013");
		processInfo.setCreateUserName("test");
		result=visitingInfoService.AddProcess(processInfo);
		System.out.println(result.getModel().toString());
	}
	
}
