import request from '@/request'


export function getArticles(query, page) {
  return request({
    url: '/public/article/list',
    method: 'post',
    data: {
      page: page.pageNumber,
      pageSize: page.pageSize,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId
    }
  })
}

export function getHotArtices() {
  return request({
    url: '/public/article/hot',
    method: 'get'
  })
}

export function getNewArtices() {
  return request({
    url: '/public/article/new',
    method: 'get'
  })
}

export function viewArticle(id) {
  return request({
    url: `/public/article/view/${id}`,
    method: 'get'
  })
}

// export function getArticlesByCategory(id) {
//   return request({
//     url: `/articles/category/${id}`,
//     method: 'post'
//   })
// }

// export function getArticlesByTag(id) {
//   return request({
//     url: `/articles/tag/${id}`,
//     method: 'post'
//   })
// }


export function publishArticle(article, token) {
  return request({
    url: '/user/publish',
    method: 'post',
    data: article
  })
}

export function listArchives() {
  return request({
    url: '/public/article/archive',
    method: 'get'
  })
}

export function getArticleById(id) {
  return request({
    url: `/public/article/view/${id}`,
    method: 'get'
  })
}

// 批量删除文章
export function removeArticlesBatch(ids, token) {
  return request({
    url: '/user/deleteArticle',
    method: 'post',
    data: ids
  });
}

// 这个传入可能有问题！！！！
export function searchArticle(search) {
  return request({
    url: '/public/article/search',
    method: 'post',
    data: {"search": search}
  })
}

export function like(articleID) {
  return request({
    url: `/sysuser/likes/${articleID}`,
    method: 'get'
  })
}
