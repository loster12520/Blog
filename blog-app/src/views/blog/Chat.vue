<template>
  <div class="chat-container">
    <div class="chat-messages" ref="messagesContainer">
      <div v-for="(message, index) in messages" :key="index" class="message-bubble">
        <div v-if="message.type === 'user'" class="user-bubble">
          {{ message.content }}
        </div>
        <div v-else class="server-bubble">
          {{ message.content }}
        </div>
      </div>
    </div>
    <div class="chat-input">
      <el-input
        v-model="inputValue"
        type="textarea"
        :rows="2"
        placeholder="请输入内容"
        @keyup.enter.native="sendMessage"
      />
      <div class="send-button">
        <el-button type="primary" @click="sendMessage" :disabled="isSending">
          发送
          <el-icon v-if="isSending"><Loading /></el-icon>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { chat } from '@/api/ai';

export default {
  data() {
    return {
      messages: [],
      inputValue: '',
      isSending: false,
    };
  },
  methods: {
    async sendMessage() {
      if (!this.inputValue.trim()) return;

      const userMessage = {
        type: 'user',
        content: this.inputValue,
        time: new Date().toLocaleTimeString(),
      };
      this.messages.push(userMessage);

      this.isSending = true;

      try {
        const response = await chat(this.inputValue);
        const serverMessage = {
          type: 'server',
          content: response.data, // 假设后端返回的响应数据在 response.data 中
          time: new Date().toLocaleTimeString(),
        };
        this.messages.push(serverMessage);
      } catch (error) {
        console.error('Error in chat:', error);
      } finally {
        this.isSending = false;
        this.inputValue = '';
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },
    scrollToBottom() {
      const container = this.$refs.messagesContainer;
      container.scrollTop = container.scrollHeight;
    },
  },
};
</script>

<style>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100%;
  background-color: #f5f5f5;
  overflow: hidden;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
  gap: 10px;
  box-sizing: border-box;
}

.message-bubble {
  margin: 10px 0;
}

.user-bubble {
  background-color: #e1f5fe;
  border-radius: 8px;
  padding: 10px 15px;
  max-width: 70%;
  align-self: flex-end;
}

.server-bubble {
  background-color: #f1f1f1;
  border-radius: 8px;
  padding: 10px 15px;
  max-width: 70%;
  align-self: flex-start;
}

.chat-input {
  display: flex;
  flex-direction: column;
  border-top: 1px solid #e0e0e0;
  padding: 15px;
  background-color: #fff;
  position: sticky;
  bottom: 0;
  z-index: 10;
}

.send-button {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
}

.el-textarea__inner {
  width: 100%;
  border-radius: 4px;
}
</style>
