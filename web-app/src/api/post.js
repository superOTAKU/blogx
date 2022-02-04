//以下是公开的API
import axios from "axios";
export function getPostList(page, rows) {
    return axios.get(`/api/post?page=${page}&rows=${rows}`)
}

export function getPostDetail(id) {
    return axios.get(`/api/post/${id}`)
}
