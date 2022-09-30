package zhongchiedu.com.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import zhongchiedu.com.dao.GeneralEsMapper;

public class ObjectImportEs {

    @Autowired
    private GeneralEsMapper generalEsMapper;
    
    /**
     * 导入es使用
     * 省略了本地的service 根查询本地数据的一下方法
     */
    public void test() {
    	
    	//调用如下方法会把查询出来的数据保存到docker中的elasticsearch中
    	this.generalEsMapper.saveAll(null);
    	
    }
    
}
