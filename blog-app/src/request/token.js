// 设置 token
export function setToken(token) {
  sessionStorage.setItem('token', token);
}

// 获取 token
export function getToken() {
  return sessionStorage.getItem('token');
}

// 移除 token
export function removeToken() {
  sessionStorage.removeItem('token');
}
