import axios from 'axios'
export function login(data) {
    return axios.post('/login', data);
}

export function refreshToken(data) {
    return axios.post('/refreshToken', data)
}