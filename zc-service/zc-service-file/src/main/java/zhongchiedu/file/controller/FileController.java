package zhongchiedu.file.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import zhongchiedu.file.pojo.FastDFSFile;
import zhongchiedu.file.util.FastDFSClient;

@RestController
@CrossOrigin // 支持跨域
@RequestMapping("/file")
public class FileController {
	/**
	 * 返回 图片的全路径
	 *
	 * @param file 页面的文件对象
	 * @return
	 */
	@PostMapping("/upload")
	public String upload(@RequestPart(value = "file") MultipartFile file) {
		try {
			// 1. 创建图片文件对象(封装)
			// 2. 调用工具类实现图片上传

			// String substring =
			// file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")
			// + 1);

			FastDFSFile fastdfsfile = new FastDFSFile(file.getOriginalFilename(), // 原来的文件名 1234.jpg
					file.getBytes(), // 文件本身的字节数组
					StringUtils.getFilenameExtension(file.getOriginalFilename()));

			String[] upload = FastDFSClient.upload(fastdfsfile);

			// upload[0] group1
			// upload[1] M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg
			// 3. 拼接图片的全路径返回

			// http://192.168.211.132:8080/group1/M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg

			// http://192.168.211.132:8080 +
			return FastDFSClient.getTrackerUrl() + "/" + upload[0] + "/" + upload[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 下载
	 *
	 * @param path
	 * @param response
	 */
	@GetMapping(value = "/download",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void download(@RequestParam("group") String group, @RequestParam("fileName") String fileName,
			HttpServletResponse response) {
		OutputStream out = null;
		InputStream in = null;
		try {
			in = FastDFSClient.downFile(group, fileName);
			out = response.getOutputStream();
			byte buf[] = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) >= 0) {
				out.write(buf, 0, len);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
