import request from '@/request'
import {setToken, removeToken} from '@/request/token'

export function login(account, password) {
  const data = {
    account,
    password
  }
  const result = request({
    url: '/public/login',
    method: 'post',
    data
  }).then(result => {
      setToken(result.data)
    }
  )
  return result
}

export function logout(token) {
  return removeToken()
}

export function getUserInfo(token) {
  return request({
    url: '/users/currentUser',
    method: 'get'
  })
}

export function register(account, nickname, password) {
  const data = {
    account,
    nickname,
    password
  }
  return request({
    url: '/public/register',
    method: 'post',
    data
  })
}
