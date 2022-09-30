package zhongchiedu.file.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import feign.Response;

@FeignClient(name = "file")
@RequestMapping("/file")
public interface FileFeign {

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String upload(@RequestPart(value = "file") MultipartFile file);

	@GetMapping(value = "/download",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Response download(@RequestParam("group") String group, @RequestParam("fileName") String flieName);

}
