package com.google.googleplaystore.protocol;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.base.MyApplication;
import com.google.googleplaystore.utils.FileUtils;
import com.google.googleplaystore.utils.HttpUtils;
import com.google.googleplaystore.utils.IOUtils;
import com.google.googleplaystore.utils.UIUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DH on 2017/7/12.
 */

public abstract class BaseProtocol<T> {
    public T loadData(int index) throws Exception {
        T result=null;
        //1.从内存读取
        result = loadDataFromMem(index);
        if (result != null) {
            return result;
        }
        //2.从磁盘-->返回,存内存
        result=loadDataFromLocal(index);
        if (result != null) {
            //说明本地有缓存
            return result;
        }
        //3.从网络-->返回,并存内存,存磁盘
        return loadDataFromNet(index);

    }

    /**
     * 从内存加载数据
     * @param index
     * @return
     */
    private T loadDataFromMem(int index) {
        T result=null;
        //找到存储结构
        MyApplication application= (MyApplication) UIUtils.getContext();
        Map<String, String> memProtocolCacheMap = application.getMemProtocolCacheMap();
        String key = generateKey(index);
        if (memProtocolCacheMap.containsKey(key)) {
            String memCacheJsonString = memProtocolCacheMap.get(key);
            result = handleData(memCacheJsonString);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * 从本地加载数据
     * @param index
     * @return
     */
    private T loadDataFromLocal(int index) {
        BufferedReader reader=null;
        //找到缓存文件
        try {
            File cacheFile = getCacheFile(index);
            if (cacheFile.exists()) {
                //文件存在,可能有有效的缓存
                reader = new BufferedReader(new FileReader(cacheFile));
                //读取缓存的生成时间
                String firstLine = reader.readLine();
                long cacheInsertTime = Long.parseLong(firstLine);
                //判断是否过期
                if ((System.currentTimeMillis() - cacheInsertTime) < Constants.PROTOCOLTIMEOUT) {
                    //存在有效的缓存
                    String diskCacheJsonString = reader.readLine();

                    //保存数据到内存
                    MyApplication myApplication = (MyApplication) UIUtils.getContext();
                    Map<String, String> memProtocolCacheMap = myApplication.getMemProtocolCacheMap();
                    memProtocolCacheMap.put(generateKey(index), diskCacheJsonString);
                    Log.d("BaseProtocol", "保存磁盘数据到内存");
                    Log.d("BaseProtocol", "从磁盘中读取数据");
                    //解析返回
                    return handleData(diskCacheJsonString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(reader);
        }
        return null;
    }

    private File getCacheFile(int index) {
        String dir = FileUtils.getDir("json");
        String fileName=getInterfaceKey()+"."+index;
        return new File(dir,fileName);
    }

    private T loadDataFromNet(int index) throws IOException {
        //1.获取okHttpClient实例
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //2.构建Request
        String url = Constants.URLS.BASEURL + getInterfaceKey();
        //定义参数对应的map
        Map<String, Object> params = getParamsMap(index);
        //拼接参数信息
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(params);
        url = url + "?" + urlParamsByMap;
        Request mGetRequest = new Request.Builder().get().url(url).build();
        //3.获取网络请求Call对象
        Call call = mOkHttpClient.newCall(mGetRequest);
        //4.执行网络请求(同步方式),获取Response
        Response response = call.execute();
        //5.处理响应 的结果
        if (response.isSuccessful()) {
            String json = response.body().string();
            //保存数据到内存
            MyApplication application = (MyApplication) UIUtils.getContext();
            Map<String, String> memProtocolCacheMap = application.getMemProtocolCacheMap();
            memProtocolCacheMap.put(generateKey(index), json);
            Log.d("BaseProtocol", "保存网络数据到内存");
            Log.d("HomeFragment", "保存数据到磁盘中");
            //保存数据本地
            BufferedWriter writer=null;
            File cacheFile = getCacheFile(index);
            writer = new BufferedWriter(new FileWriter(cacheFile));
            //写第一行
            writer.write(System.currentTimeMillis()+"");
            writer.newLine();
            writer.write(json);

            return handleData(json);
        }else {
            return null;
        }
    }

    @NonNull
    public Map<String, Object> getParamsMap(int index) {
        Map<String, Object> params = new HashMap<>();
        params.put("index", index);
        return params;
    }

    /**
     * 生成缓存的唯一的索引的key
     * @param index
     * @return
     */
    public String generateKey(int index) {

        return getInterfaceKey()+"."+index;
    }

    protected abstract T handleData(String json);

    public abstract String getInterfaceKey();
}
