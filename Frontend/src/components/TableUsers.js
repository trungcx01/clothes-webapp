import {useEffect, useState} from "react";
import {fetchAllUsers, fetchImportExcelFile, fetchSearchUser} from "../services/UserService";
import Table from "react-bootstrap/Table";
import ModalSubmitForm from "./ModalSubmitForm";
import ModalDeleteUser from "./ModalDeleteUser";
import dayjs from "dayjs";
import _ from "lodash";
import {CSVLink} from "react-csv";
import * as events from "events";

const TableUsers = () => {
    const [listUsers, setListUsers] = useState([]);
    const [sortField, setsortField] = useState([]);
    const [sortBy, setSortBy] = useState([]);
    const [isShowModalSubmitForm, setIsShowModalSubmitForm] = useState(false);
    const [isShowModalDelete, setIsShowModalDelete] = useState(false);
    const [originalUser, setOriginalUser] = useState({});
    const [deleteUserId, setDeleteUserId] = useState([]);

    const [keyword, setKeyWord] = useState("");
    const [dataExport, setDataExport] = useState([]);
    const handleClose = () => {
        // setIsShowModalAddNew(false);
        setIsShowModalSubmitForm(false);
        setIsShowModalDelete(false);
    }
    
    const displayDataUser = (user) => {
        setOriginalUser(user);
        setIsShowModalSubmitForm(true);
    }

    const confirmDeleteUser = (user) =>{
        setIsShowModalDelete(true);
        setDeleteUserId(user.userId);
    }

    const createNewUser = () =>{
        setIsShowModalSubmitForm(true);
    }

    const getAllUsers = async () => {
        try {
            const res = await fetchAllUsers(sortField, sortBy);
            if (res && res.data){
                console.log(res.data);
                setListUsers(res.data);
            }
        } catch (error){
            console.error("Error fetching users:", error);
        }
    };

    const handleSortUsers = (sortField) =>{
        setsortField(sortField);
        setSortBy(sortBy === "desc" ? "asc" : "desc");
    }
    const handleSearch = async (e) =>{
        const debounceSearch = _.debounce(async () =>{
            const res = await fetchSearchUser(e.target.value);
            setListUsers(res.data);
        }, 500)
        debounceSearch();
    }

    const formatDate = (sqlDate) => {
        return dayjs(sqlDate).format('DD/MM/YYYY HH:mm:ss');
    }

    // const dataToExport = [
    //     ["User ID", "Username", "Password", "Fullname", "Gender", "Email", "Role", "Created At", "Updated At"],
    //     ...listUsers.map((item) =>[
    //         item.userId,
    //         item.username,
    //         item.password,
    //         item.fullname,
    //         item.gender,
    //         item.email,
    //         item.roles.map(role => role.roleName).join(', '),
    //         formatDate(item.createdAt),
    //         formatDate(item.updatedAt)
    //     ])
    // ];

    const importData = async (e) => {
        const file = e.target.files[0];
        try {
            const res = await fetchImportExcelFile(file);
            console.log("Đã import file " + file.name + " thành công");
        } catch(err) {
            console.error("Lỗi: " + err);
        }
    }

    useEffect(() => {
        getAllUsers();
    }, [sortBy, sortField, isShowModalSubmitForm, isShowModalDelete]);


    return (
        <>
            <div className="my-3 d-flex justify-content-between align-items-center">
                <strong>List User</strong>
                <div className="group-btn">
                    <label htmlFor="import" className="btn btn-secondary mx-1">
                        <i className="fa-solid fa-file-import"></i> Import
                    </label>
                    <input id="import" type="file" hidden={true} onChange={importData}/>
                    {/* <CSVLink data={dataToExport}
                             filename={"users.csv"}
                             className="btn btn-info mx-1"
                             ><i className="fa-solid fa-file-arrow-down"></i> Export</CSVLink> */}
                    <button
                        className="btn btn-primary mx-1"
                        onClick={() =>createNewUser()}>
                        <i className="fa-regular fa-square-plus"></i> Add new
                    </button>
                </div>
            </div>

            <div className="col-4 my-3">
                <input className="form-control" placeholder="Search user here..."
                onChange={handleSearch}/>
            </div>

            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>
                        Username
                        <i onClick={() => handleSortUsers('username')} className="fa-solid fa-sort"></i>

                    </th>
                    <th>
                        Fullname
                        <i onClick={() => handleSortUsers('fullname')} className="fa-solid fa-sort"></i>
                    </th>
                    <th>Gender</th>
                    <th>
                        Email
                        <i onClick={() => handleSortUsers('email')} className="fa-solid fa-sort"></i>
                    </th>
                    {/* <th>Password</th> */}
                    <th>Roles</th>
                    <th>
                        Created At
                        <i onClick={() => handleSortUsers('createdAt')} className="fa-solid fa-sort"></i>
                    </th>
                    <th>
                        Updated At
                        <i onClick={() => handleSortUsers('updatedAt')} className="fa-solid fa-sort"></i>
                    </th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {listUsers &&
                    listUsers.length > 0 &&
                    listUsers.map((item, index) => {
                        return (
                            <tr key={`users-${index}`}>
                                <td>{item.userId}</td>
                                <td>{item.username}</td>
                                <td>{item.fullname}</td>
                                <td>{item.gender}</td>
                                <td>{item.email}</td>
                                {/* <td>{item.password}</td> */}
                                <td>{item.roles.map(role => role.roleName).join(', ')}</td>
                                <td>{formatDate(item.createdAt)}</td>
                                <td>{formatDate(item.updatedAt)}</td>
                                <td className="d-flex">
                                    <button className="btn btn-info mx-3" onClick={() => displayDataUser(item) }>Edit</button>
                                    <button className="btn btn-secondary" onClick={() => confirmDeleteUser(item) }>Delete</button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </Table>

            <ModalSubmitForm show={isShowModalSubmitForm} handleClose={handleClose}
                             originalUser={originalUser}
                             getAllUsers={getAllUsers}
            />

            <ModalDeleteUser show={isShowModalDelete} handleClose={handleClose}
                           deleteUserId={deleteUserId} getAllUsers={getAllUsers}
            />
        </>
    );

};

export default TableUsers;