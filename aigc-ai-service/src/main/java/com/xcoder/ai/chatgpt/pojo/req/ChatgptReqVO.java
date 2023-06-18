package com.xcoder.ai.chatgpt.pojo.req;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ChatgptReqVO {

    private String conversation_id;

    private String parent_message_id;

    private List<Message> messages = new ArrayList<>();

    private Object arkose_token = null;

    private Boolean history_and_training_disabled = false;

    private String action = "next";

    private String model = "text-davinci-002-render-sha";

    private Integer timezone_offset_min = -480;

    public ChatgptReqVO() {
        this.parent_message_id = UUID.randomUUID().toString();
    }

    public ChatgptReqVO(String content) {
        this.parent_message_id = UUID.randomUUID().toString();
        this.messages.add(new Message(content));
    }

    public ChatgptReqVO(String content, String conversationId) {
        this.parent_message_id = UUID.randomUUID().toString();
        this.messages.add(new Message(content));
        if (!StrUtil.isEmpty(conversationId)) {
            this.conversation_id = conversationId;
        }
    }

    @Data
    public static class Message {

        private Object metadata = new Object();

        private Author author = new Author();

        private String id;

        private Content content;

        public Message() {
            this.id = UUID.randomUUID().toString();
        }

        public Message(String content) {
            this.id = UUID.randomUUID().toString();
            this.content = new Content(content);
        }

    }

    @Data
    public static class Author {

        private String role = "user";

    }

    @Data
    public static class Content {

        private String content_type = "text";

        private List<String> parts = new ArrayList<>();

        public Content() {
        }

        public Content(String content) {
            this.parts.add(content);
        }
    }

}
