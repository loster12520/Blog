import request from '@/request'

export function getCommentsByArticle(id) {
  return request({
    url: `/comments/article/${id}`,
    method: 'get'
  })
}

export function publishComment(comment, token) {
  return request({
    headers: { Authorization: token },
    url: '/comments/create/change',
    method: 'post',
    data: comment
  })
}

// 新增：删除评论接口
// @/api/comment.js
export function deleteCommentAPI(commentId, token) {
  // 如果后端不需要 Bearer 前缀，就直接传 token。若需要，就加上 'Bearer ' + token
  return request({
    headers: { Authorization: token },
    url: '/comments/delete',
    method: 'post',
    data: { commentId }
  }).then(res => {
    // 这里直接返回后端的 JSON 数据对象
    return res.data
  })
}
