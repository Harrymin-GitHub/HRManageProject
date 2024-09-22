import request from '@/utils/request'
import {parseStrEmpty} from "@/utils/harrymin";

// 查询用户列表
export function listEmployee(query) {
  return request({
    url: '/system/employee/list',
    method: 'get',
    params: query
  })
}

// 新增用户
export function addEmployee(data) {
  return request({
    url: '/system/employee',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateEmployee(data) {
  return request({
    url: '/system/employee',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delEmployee(employeeId) {
  return request({
    url: '/system/employee/' + employeeId,
    method: 'delete'
  })
}

// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/employee/deptTree',
    method: 'get'
  })
}
