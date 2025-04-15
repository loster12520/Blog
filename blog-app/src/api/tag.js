import request from '@/request';

// 获取所有标签
export function getAllTags() {
  return request({
    url: '/tags',
    method: 'get'
  });
}

// 获取所有标签详情
export function getAllTagsDetail() {
  return request({
    url: '/tags/detail',
    method: 'get'
  });
}

// 获取热门标签
export function getHotTags() {
  return request({
    url: '/tags/hot',
    method: 'get'
  });
}

// 获取单个标签
export function getTag(id) {
  return request({
    url: `/tags/${id}`,
    method: 'get'
  });
}

// 获取单个标签详情
export function getTagDetail(id) {
  return request({
    url: `/tags/detail/${id}`,
    method: 'get'
  });
}

// 添加新标签：直接从 localStorage 获取 token 并传入请求头
export function addTag(data) {
  const token = localStorage.getItem('token'); // 直接获取 token
  return request({
    url: '/tags/add',
    method: 'post',
    data: data,
    headers: {
      Authorization: token
    }
  });
}

// 删除标签
// 删除标签
export function removeTag(id) {
  const token = localStorage.getItem('token'); // 获取 token
  console.log(`尝试删除标签，ID: ${id}，Token: ${token}`);
  return request({
    url: `/tags/${id}`,
    method: 'delete',
    headers: {
      Authorization: token
    }
  });
}

