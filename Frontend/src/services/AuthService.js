import axios from "axios";

const loginAPI = ( username, password ) =>{
    return axios.post(`http://localhost:8080/api/auth/login`, { username, password });
}

const logoutAPI = ( ) =>{
    return axios.post(`http://localhost:8080/api/logout`);
}

export {loginAPI, logoutAPI};