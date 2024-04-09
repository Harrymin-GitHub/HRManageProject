import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/harrymin";

export function listProject(query) {
  return request({
    url: '/system/project/list',
    method: 'get',
    params: query
  })
}

export function getProject(projectId) {
  return request({
    url: `/system/project/` + parseStrEmpty(projectId),
    method: 'get'
  })
}

export function addProject(data) {
  return request({
    url: '/system/project',
    method: 'post',
    data: data // 传回后端的实体数据
  })
}

export function updateProject(data) {
  return request({
    url: '/system/project',
    method: 'put',
    data: data
  })
}

export function delProject(projectId) {
  return request({
    url: `/system/project/` + projectId,
    method: 'delete'
  })
}

// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/project/deptTree',
    method: 'get'
  })
}
