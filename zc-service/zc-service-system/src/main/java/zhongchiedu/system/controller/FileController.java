package zhongchiedu.system.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import feign.Response;
import zhongchiedu.file.feign.FileFeign;

@RequestMapping("/file")
@RestController
public class FileController {

	@Autowired
	private FileFeign fileFeign;

	@RequestMapping("/upload")
	public String uploatTest(@RequestPart(value = "file") MultipartFile file) {
		// [group1, M00/00/00/wKgyyGBYSyKAEaJ_AAB3P5TNCG4477.jpg]
		return this.fileFeign.upload(file);

	}

	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam("group") String group,
			@RequestParam("fileName") String fileName) throws IOException {

		ResponseEntity<byte[]> result = null;
		InputStream inputStream = null;
		try {
			// feign文件下载
			Response response = this.fileFeign.download(group, fileName);
			Response.Body body = response.body();
			inputStream = body.asInputStream();
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			HttpHeaders heads = new HttpHeaders();
			heads.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=fliay.jpg");
			heads.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

			result = new ResponseEntity<byte[]>(b, heads, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;

	}

}
