package com.xcoder.ai.chatgpt.service;


import com.alibaba.fastjson2.JSONObject;
import com.xcoder.ai.chatgpt.constant.ChatgptConstant;
import com.xcoder.ai.chatgpt.pojo.req.ChatgptReqVO;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ChatgptService {

    private final OkHttpClient httpClient = initHttpClient();

    public OkHttpClient initHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder()
                .readTimeout(60 * 10, TimeUnit.SECONDS)
                .connectTimeout(60 * 10, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public void ask(String content, String conversationId) {
        try {
            Response response = httpClient.newCall(createRequest(ChatgptConstant.CHATGPT_ASK_URL, content, conversationId)).execute();
            // 处理响应
            String responseBody = response.body().string();
            // 对响应进行处理...
            System.out.println(responseBody);
            // 关闭响应体
            response.body().close();
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }

    }

    private Request createRequest(String url, String content, String conversationId) {
        ChatgptReqVO reqVO = createRequest(content, conversationId);
        System.out.println(JSONObject.toJSONString(reqVO));
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .headers(createHeaders())
                .post(RequestBody.create(mediaType, JSONObject.toJSONString(reqVO)))
                .build();
        return request;
    }

    private Headers createHeaders() {
        Map<String, String> maps = new HashMap<>();
        maps.put("Authorization", ChatgptConstant.AUTH_TOKEN);
        maps.put("User-Agent", ChatgptConstant.USER_AGENT);
        return Headers.of(maps);
    }

    private ChatgptReqVO createRequest(String content, String conversationId) {
        return new ChatgptReqVO(content, conversationId);
    }

    public static void main(String[] args) {
        ChatgptService service = new ChatgptService();
        service.ask("你是一位资深的记者，请你写一篇社会评论文章，以“诬陷”为题。", "");
    }


}
