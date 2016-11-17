package cmcc.oa.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cmcc.oa.base.JsonResult;
import cmcc.oa.dao.ProcessInfoMapper;
import cmcc.oa.dao.VisitingInfoMapper;
import cmcc.oa.entity.ProcessInfo;
import cmcc.oa.entity.VisitingInfo;
import cmcc.oa.service.VisitingInfoService;
import cmcc.oa.utils.PropertiesUtil;
import cmcc.oa.utils.TimeUtils;
import cmcc.oa.vo.MMS;
import cmcc.oa.vo.VisitingInfoVo;

@Service
public class VisitingInfoServiceImpl implements VisitingInfoService {

	@Autowired
	private VisitingInfoMapper visitingInfoMapper;
	@Autowired
	private ProcessInfoMapper processInfoMapper;

	private Logger logger = Logger.getLogger(this.getClass());

	private static String fileUploadPath;

	/**
	 * @method 查询来访人员记录
	 * @return JsonResult
	 */
	@Override
	public JsonResult QueryVisitInfo(String codeId) {
		// TODO Auto-generated method stub
		JsonResult result = new JsonResult(true, "", "");
		VisitingInfo visitingInfo = new VisitingInfo();
		visitingInfo.setCodeId(codeId);
		VisitingInfoVo resultVo = visitingInfoMapper.findByCodeId(visitingInfo);
		resultVo=showVisitInfo(resultVo);
		result.setSuccess(true);
		if(resultVo == null){
			result.setSuccess(false);
			result.setMessage("没有找到预约人员");
		}else if(!resultVo.getVisitStatus().equals("1")){
			//result.setSuccess(false);
			result.setMessage("不在预约时间内");
		}
		result.setModel(resultVo);
		return result;
	}

	/**
	 * 登记来访人员进入信息
	 * 
	 * @return JsonResult
	 */
	@Override
	public JsonResult AddComVisitInfo(VisitingInfo visitingInfo) {
		// TODO Auto-generated method stub
		JsonResult result = new JsonResult();
		if (visitingInfo != null) {
			try {
				visitingInfo.setCreateTime(new Date());
			    visitingInfoMapper.updateByPrimaryKeySelective(visitingInfo);
				result.setMessage("记录添加成功");
				result.setSuccess(true);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("插入visitingInfo表失败！");
			}
		} else {
			result.setMessage("记录添加失败");
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * @method 登记来访人员出去信息
	 * @author zhaieryuan
	 * @param VisitingInfo
	 * @return JsonResult
	 */
	@Override
	public void AddOutVisitInfo(VisitingInfo visitingInfo) {
		// TODO Auto-generated method stub
		if (visitingInfo != null) {
			visitingInfoMapper.updateByPrimaryKeySelective(visitingInfo);
		} else {
			logger.error("visitingInfo表update失败");
		}
	}
	/**ss
	 * 
	 */

	/**
	 * @method 添加流程实例并返回流程id
	 * @author zhaieryuan
	 * @param ProcessInfo
	 * @return JsonResult
	 */
	@Override
	public JsonResult AddProcess(ProcessInfo processInfo) {
		// TODO Auto-generated method stub
		JsonResult result = new JsonResult();
		if(processInfo !=null){
			processInfo.setCreateTime(new Date());
			processInfoMapper.insert(processInfo);
			result.setMessage("流程添加成功");
			result.setModel(processInfo.getId());
			result.setSuccess(true);
		} else {
			result.setMessage("流程添加失败");
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 来访信息批量插入
	 */

	public JsonResult BatchAddVisitInfo(List<VisitingInfo> visitingInfos) {
		// TODO Auto-generated method stub
		JsonResult result = new JsonResult();
		if (visitingInfos.size() > 0) {
			result.setSuccess(true);
			result.setMessage("批量插入成功");
			visitingInfoMapper.batchInsert(visitingInfos);
		} else {
			result.setSuccess(false);
			result.setMessage("批量插入失败");
		}
		return result;
	}

	/**
	 * @method 创建文件夹
	 * @param fileUploadPath
	 * @param extension
	 * @return
	 */
	public java.io.File createFile(String fileUploadPath, String fileName) {
		String filePath = fileUploadPath + java.io.File.separator + fileName;
		java.io.File file = new java.io.File(filePath);
		if (file.exists()) {
			file.delete();
			createFile(fileUploadPath, fileName);
		}
		return file;
	}

	/**
	 * @method 接收上传图片
	 * @author zhaieryuan
	 * @param request
	 *            ,response
	 */
	@Override
	public void multipleFilesUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileUPloadPath = PropertiesUtil.getAppByKey("QR_CODE_UPLOAD_PATH");// 文件上传保存的地址
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<List<MultipartFile>> iter = multiRequest.getMultiFileMap().values().iterator();
			while (iter.hasNext()) {
				// 取得上传文件
				List<MultipartFile> files = iter.next();
				if (files != null) {
					for (MultipartFile file : files) {
						// 取得当前上传文件的文件名称
						String myFileName = file.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (myFileName.trim() != "") {
							// 获取文件名
							String fileName = file.getOriginalFilename();
							// 获取文件后缀名
							String extension = FilenameUtils.getExtension(fileName);
							// 定义上传路径
							java.io.File localFile = createFile(fileUPloadPath, fileName);
							file.transferTo(localFile);
						}
					}
				}
			}
		}
	}

	/**
	 * 查询未发送短信的来访人员信息并更新状态
	 */
	@Override
	public List<MMS> getUnsentVisitInfo() {
		List<MMS> result = new ArrayList<>();
		List<Long> ids = new ArrayList<>();
		List<Long> errorIds = new ArrayList<>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat textFmt = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		// 查询未发送的消息
		VisitingInfo record = new VisitingInfo();
		record.setIsSendMess(0);
		List<VisitingInfo> selectList = visitingInfoMapper.selectList(record);
		if (selectList.isEmpty()) {
			return result;
		}
		// 构造返回对象
		for (VisitingInfo itemInfo : selectList) {
			MMS mms = new MMS();
			mms.setUser_number(itemInfo.getMobile());
			mms.setUser_name(itemInfo.getUserName());
			mms.setSend_date(fmt.format(itemInfo.getCreateTime()));
			mms.setStart_time(textFmt.format(itemInfo.getStartTime()));
			mms.setEnd_time(textFmt.format(itemInfo.getEndTime()));
			byte[] imageData = readImageBytes(itemInfo.getCodeId());
			// 读取图像失败时记为异常
			if (imageData == null) {
				errorIds.add(itemInfo.getId());
				continue;
			}

			mms.encodeData2Mms_data(imageData);
			result.add(mms);

			ids.add(itemInfo.getId());
		}

		// 更新未发送数据状态
		Map<String, Object> params = new HashMap<>();
		params.put("isSendMess", 1);

		Map<String, Object> conditions = new HashMap<>();
		conditions.put("ids", ids);
		params.put("conditions", conditions);
		visitingInfoMapper.updateByParameters(params);

		// 更新异常数据状态
		if (!errorIds.isEmpty()) {
			Map<String, Object> errParams = new HashMap<>();
			errParams.put("isSendMess", 2);

			Map<String, Object> errConditions = new HashMap<>();
			errConditions.put("ids", errorIds);
			errParams.put("conditions", errConditions);
			visitingInfoMapper.updateByParameters(errParams);
		}

		return result;
	}

	private byte[] readImageBytes(String codeId) {
		if (StringUtils.isEmpty(fileUploadPath)) {
			fileUploadPath = PropertiesUtil.getAppByKey("QR_CODE_UPLOAD_PATH");
		}

		boolean done = false;
		String fileName = codeId + ".jpg";
		File file = new File(fileUploadPath, fileName);
		if (!file.exists()) {
			logger.error("无法从" + file.toPath() + " 读取图片，文件不存在");
			return null;
		}

		try (FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {

			long fileSize = channel.size();
			ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
			channel.read(buffer);
			done = true;
			return buffer.array();
		} catch (IOException e) {
			if (!done) {
				String msg = "读取二维码图片失败,codeId:" + codeId;
				logger.error(msg);
				return null;
			}
		}
		return null;
	}

	/**
	 * 判断用户显示来访状态
	 */
	@Override
	public VisitingInfoVo showVisitInfo(VisitingInfoVo visitingInfoVo) {
		// TODO Auto-generated method stub
		Date now = new Date();
		if(visitingInfoVo.getUserStatus()==0){
			if (now.before(visitingInfoVo.getStartTime()))
			{
				//当前时间比预约时间早
				visitingInfoVo.setVisitStatus("3");
			}
			else if (now.after(visitingInfoVo.getEndTime()))
			{
				//比当前预约时间晚
				visitingInfoVo.setVisitStatus("2");
			}else{
				//在预约时间之内
				visitingInfoVo.setVisitStatus("1");
			}
		}else 
			//如果用户不在预约状态
			visitingInfoVo.setVisitStatus("0");
		
		/*原来代码
		 * //获取系统的当前时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String CurrentDate=sdf.format(new Date());
		if(visitingInfoVo.getUserStatus()==0){
			if(TimeUtils.compare_date(sdf.format(visitingInfoVo.getStartTime()),CurrentDate)){
				//当前时间比预约时间早
				visitingInfoVo.setVisitStatus("3");
			}else if(TimeUtils.compare_date(CurrentDate,sdf.format(visitingInfoVo.getEndTime()))){
				//比当前预约时间晚
				visitingInfoVo.setVisitStatus("2");
			}else {
				//在预约时间之内
				visitingInfoVo.setVisitStatus("1");
			}
		}else {
			//如果用户不在预约状态
			visitingInfoVo.setVisitStatus("1");
		}
		*/
		return visitingInfoVo;
	}

}
