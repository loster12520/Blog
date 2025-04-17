import request from '@/request'
import {removeToken} from '@/request/token'

export function login(account, password) {
  const data = {
    account,
    password
  }
  return request({
    url: '/public/login',
    method: 'post',
    data
  })
}

export function logout(token) {
  return removeToken()
}

export function getUserInfo(token) {
  return request({
    headers: {'Authorization': token},
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
    url: '/register',
    method: 'post',
    data
  })
}
