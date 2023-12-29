import customizeAxios from "./customize-axios";

const fetchAllUsers = (sortField, sortBy) => {
  return customizeAxios.get(`/api/users?sortField=${sortField}&sortBy=${sortBy}`);
}

const fetchUserById = (userId) => {
  return customizeAxios.get(`/api/user/${userId}`)
}

const fetchUpdateUser = (userId, user) =>{
  return customizeAxios.put(`/api/update-user/${userId}`, user);
}

const fetchDeleteUser = (userId) =>{
  return customizeAxios.delete(`/api/delete-user/${userId}`);
}

const fetchCreateUser = (user) =>{
  return customizeAxios.post(`/api/register-user`, user);
}

const fetchSearchUser = (keyword) =>{
  return customizeAxios.get(`/api/search-users?keyword=${keyword}`);
}

const fetchImportExcelFile = (file) => {
  const formData = new FormData();
  formData.append("file", file); // ở đây "file" phải khớp với tên mà server dùng để trích xuất file từ request (ở đây là `@RequestParam("file")`)

  // set tùy chọn headers
  const config = {
    headers: { 'Content-Type': 'multipart/form-data' }
  }

  return customizeAxios.post("/api/import-users", formData, config);
}

const fetchAllRoles = () =>{
  return customizeAxios.get(`/api/roles`);
}
export {fetchAllUsers, fetchUserById, fetchUpdateUser, fetchDeleteUser, fetchCreateUser, fetchSearchUser, fetchImportExcelFile, fetchAllRoles};