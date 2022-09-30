package zhongchiedu.com.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class CreateStaticPage {

	@Autowired
	private TemplateEngine templateEngine;

	public void createPageHtml() {

		 // 1.上下文
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel("");
        context.setVariables(dataModel);
        // 2.准备文件
        File dir = new File("");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(dir, "aaa" + ".html");
        // 3.生成页面
        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private Map<String, Object> buildDataModel(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
