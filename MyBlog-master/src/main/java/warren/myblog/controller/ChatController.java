package warren.myblog.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/public/ai")
public class ChatController {
    private final ChatClient chatClient;

    //会把内容一次性响应回来，会慢，流式更适合
    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultSystem(
                "您是一个IT技术博客的咨询师"
                        + "请以友好，乐于助人方式来回复，您正在通过在线聊天系统与客户互动。"
        ).build();
    }

    @Operation(tags = "AI", summary = "文本聊天")
    @PostMapping(value="/chat",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestBody String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}