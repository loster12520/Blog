import request from '@/request'

export function getCommentsByArticle(id) {
  return request({
    url: `/public/comment/listComments/${id}`,
    method: 'get'
  })
}

export function publishComment(comment, token) {
  return request({
    url: '/user/remark',
    method: 'post',
    data: comment
  })
}

// 新增：删除评论接口
// @/api/comment.js
export function deleteCommentAPI(commentId, token) {
  // 如果后端不需要 Bearer 前缀，就直接传 token。若需要，就加上 'Bearer ' + token
  return request({
    url: '/user/deleteComment',
    method: 'post',
    data: { commentId }
  })
}
