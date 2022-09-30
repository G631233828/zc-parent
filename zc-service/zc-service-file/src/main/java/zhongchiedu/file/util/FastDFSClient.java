package zhongchiedu.file.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import zhongchiedu.file.pojo.FastDFSFile;


public class FastDFSClient {

    static {
        //从classpath下获取文件对象获取路径
        String path = new ClassPathResource("fdfs_client.conf").getPath();
        try {
            ClientGlobal.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //图片上传
    public static String[] upload(FastDFSFile file) {
        try {
        	//创建一个Tracker访问的客户端对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient访问TrackerServer服务，获取连接信息
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过trackerServer的连接信息可以获取Storage的连接信息，创建StorageClient对象存储Storage的连接信息
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //参数1 字节数组
            //参数2 扩展名(不带点)
            //参数3 元数据( 文件的大小,文件的作者,文件的创建时间戳)
            NameValuePair[] meta_list = new NameValuePair[]{new NameValuePair(file.getAuthor()), new NameValuePair(file.getName())};
            //通过StorageClient访问Storage实现文件上传并且获取文件上传后的存储信息
            String[] strings = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);

            return strings;// strings[0]==group1  strings[1]=M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //图片下载
    public static InputStream downFile(String groupName, String remoteFileName) {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            //3.创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //5.创建stroageserver 对象
            //6.创建storageclient 对象
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //7.根据组名 和 文件名 下载图片

            //参数1:指定组名
            //参数2 :指定远程的文件名
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            return byteArrayInputStream;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //图片删除

    public static void deleteFile(String groupName, String remoteFileName) {
        try {
            //3.创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //5.创建stroageserver 对象
            //6.创建storageclient 对象
            StorageClient storageClient = new StorageClient(trackerServer, null);
            int i = storageClient.delete_file(groupName, remoteFileName);
            if (i == 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据组名获取组的信息

    public static StorageServer getStorages(String groupName) {
        try {
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();

            //参数1 指定traqckerserver 对象
            //参数2 指定组名
            StorageServer group1 = trackerClient.getStoreStorage(trackerServer, groupName);
            return group1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据文件名和组名获取文件的信息

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
        	//创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过TrackerServer获取Storage信息，创建StorageClient对象存储Storage信息
            StorageClient storageClient = new StorageClient(trackerServer, null);

            //参数1 指定组名
            //参数2 指定文件的路径
            //获取文件信息
            FileInfo fileInfo = storageClient.get_file_info(groupName, remoteFileName);
            return fileInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    public static void main(String[] args) throws IOException {
		FileInfo f = getFile("group1","M00/00/00/wKgBgGL957GAUXO2AATmXdWynt0780.jpg");
		System.out.println(f.getFileSize());
    	InputStream downFile = downFile("group1","M00/00/00/wKgBgGL957GAUXO2AATmXdWynt0780.jpg");
    	FileOutputStream os = new FileOutputStream("E:/22.jpg");
    	byte[] buffer = new byte[1024];
    	try {
			while(downFile.read(buffer)!=-1) {
				os.write(buffer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	os.flush();
    	os.close();
    	downFile.close();
////    --------------------------
//    	File f = new File("E:\\123.jpg");
//    	 BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
//    	    ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//    	    byte[] temp = new byte[1024];
//    	    int size = 0;
//    	    while((size = in.read(temp)) != -1){
//    	        out.write(temp, 0, size);
//    	    }
//    	    in.close();
//    	    byte[] content = out.toByteArray();
//    	
//    	
//    	
//    	
//    	FastDFSFile fastdfsfile = new FastDFSFile("1", // 原来的文件名 1234.jpg
//    			content, // 文件本身的字节数组
//				"jpg");
//    	String[] upload = upload(fastdfsfile);
//    	System.out.println(upload[0]);
//    	System.out.println(upload[1]);

    	
    }
    
    
    
//    group1
//    M00/00/00/wKgBgGL957GAUXO2AATmXdWynt0780.jpg
//    
    
    
    
    

    //根据文件名和组名 获取组信息的数组信息
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName){
        try {
            //3.创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();

            ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
            return group1s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //获取tracker 的ip和端口的信息
    //http://192.168.211.132:8080
    public static String getTrackerUrl(){
        try {
            //3.创建trackerclient对象
            TrackerClient trackerClient = new TrackerClient();
            //4.创建trackerserver 对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //tracker 的ip的信息
            String hostString = trackerServer.getInetSocketAddress().getHostString();

            //http://192.168.211.132:8080/group1/M00/00/00/wKjThF1aW9CAOUJGAAClQrJOYvs424.jpg img
            int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
            return "http://" + hostString + ":" + g_tracker_http_port;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
