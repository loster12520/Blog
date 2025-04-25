import request from '@/request'

const baseUrl = 'http:/localhost:8888/'

export async function chat(message) {
  const response = await fetch(`${baseUrl}/public/ai/chat`, {
    method: "POST",
    headers: {
      "Content-Type": 'text/plain',
    },
    body: message.trim(),
  });

  if (!response.body) {
    return;
  }
  return response.body.getReader()
}
