import request from '@/request'

export function chat(message) {
  return request({
    url: '/public/ai/chat',
    method: 'post',
    headers: {
      'Content-Type': 'text/plain'
    },
    data: message.trim()
  })
}
