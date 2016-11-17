package oa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 *
 * @author renlinggao
 * @Date 2016年10月21日
 */
public class QRCode {
	// @Test
	// public void shouldGetBitmapFileFromText() throws Exception {
	// File file = QRCodeUtil.from("www.example.org").to(ImageType.JPG).file();
	// Assert.assertNotNull(file);
	// }
	//
	@Test
	public void test() {
		Map<String, String> params = new HashMap<>();
		params.put("jasonArray", "[]");
		List<File> files = new ArrayList<>();
		File file = new File("D:\\imageTest\\1.jpg");
		files.add(file);
		String xx = HttpClientUtil.postFormAndFiles("http://192.168.2.108:8080/oa/visitingInfo/batchAddComVisitInfo.do",
				params, files);
		System.out.println(xx);
	}

}
