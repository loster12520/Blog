package warren.myblog.utils;

import com.alibaba.fastjson2.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.qiniu.storage.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Component
public class QiniuUtils {

    public static  final String url = "http://suotpk36w.hn-bkt.clouddn.com/";

    @Value("${qiniu.accessKey}")
    private  String accessKey;
    @Value("${qiniu.accessSecretKey}")
    private  String accessSecretKey;
    @Value("${qiniu.bucketName}")
    private String bucket;

        public  boolean upload(MultipartFile file,String fileName) {

            //构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.autoRegion());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传

            //默认不指定key的情况下，以文件内容的hash值作为文件名
            try {
                byte[] uploadBytes = file.getBytes();
                Auth auth = Auth.create(accessKey, accessSecretKey);
                String upToken = auth.uploadToken(bucket);
                Response response = uploadManager.put(uploadBytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
}
