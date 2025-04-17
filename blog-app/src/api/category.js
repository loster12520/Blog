import request from '@/request'

export function getAllCategorys() {
  return request({
    url: '/public/category/list',
    method: 'get',
  })
}

export function getAllCategorysDetail() {
  return request({
    url: '/public/category/detail',
    method: 'get',
  })
}

// export function getCategory(id) {
//   return request({
//     url: `/categorys/${id}`,
//     method: 'get',
//   })
// }

export function getCategoryDetail(id) {
  return request({
    url: `/public/category/detail/${id}`,
    method: 'get',
  })
}
