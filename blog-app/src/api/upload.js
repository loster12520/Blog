import request from '@/request'
import {getToken} from '@/request/token'

export function upload(formdata) {
  return request({
    headers: {
      'Content-Type': 'multipart/form-data',
      'token': getToken()
    },
    url: '/user/upload',
    method: 'post',
    data: formdata
  })
}
