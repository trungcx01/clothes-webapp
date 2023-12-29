import {Button, Modal} from "react-bootstrap";
import {useEffect, useState} from "react";
import {fetchAllRoles, fetchCreateUser, fetchDeleteUser, fetchUpdateUser} from "../services/UserService";

const ModalSubmitForm = (props) =>{
    const {show, getAllUsers, handleClose, originalUser} = props;
    const [result, setResult] = useState(null);
    const [allRoles, setAllRoles] = useState([]);
    const [user, setUser] = useState({
        userId: '', username: '', password: '', email: '', gender: '', fullname: '', createdAt: '', updateAt: '', role: null
    });

    const [listSelectedRoles, setListSelectedRoles] = useState([]);
    const EMPTY_USER = {
        userId: '',
        username: '',
        password: '',
        email: '',
        gender: '',
        fullname: '',
        createdAt: '',
        updateAt: '',
        role: null
    };

    const handleSubmitForm = async() => {
        if (!user.userId){
            const createUserResponse = await fetchCreateUser(user);
            if (createUserResponse.status === 200) {
                console.log('Create user success:', createUserResponse.data);
                setResult(createUserResponse.data);
                handleClose();
            } else {
                console.log('Failed to create user:', createUserResponse);
            }
        } else {
            const updateUserResponse = await fetchUpdateUser(originalUser.userId, user);
            if (updateUserResponse.status === 200) {
                console.log('Update user success:', updateUserResponse.data);
                setResult(updateUserResponse.data);
                handleClose();
            } else {
                console.log('Failed to update user:', updateUserResponse);
            }
        }
        setUser(EMPTY_USER);
    }

    useEffect(() => {
        const getAllRoles = async() =>{
            var res = await fetchAllRoles();
            if (res.status === 200){
                setAllRoles(res.data);
            }
        }
        getAllRoles();
    }, []);

    useEffect(() => {
        if (!originalUser){
            setUser(EMPTY_USER);
        }
        else {
            setUser(originalUser);
        }
    }, [originalUser]);

    useEffect(() => {
        if (result){
            getAllUsers("", "");
        }
    }, [result]);

    const handleChange = (e) => {
        setUser({
            ...user,
            [e.target.name]: e.target.value
        });
    }

    const handleChangeRole = (e) =>{
        // const listSelectedRoleId = Number(e.target.value);
        // const selectedRole = allRoles.find(role => role.roleId === selectedRoleId);
        // setUser({...user, role: selectedRole});
        // console.log(selectedRoleId);
        // console.log(selectedRole)
        console.log(e.target.value);
    }


    return (
        <Modal show={show}
               onHide={handleClose}
               backdrop="static"
               keyboard={false}
        >
            <Modal.Header closeButton>
                <Modal.Title>Edit User</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="container">
                    <form>
                        <div className="form-group mb-2">
                            <label htmlFor="username">Username</label>
                            <input type="text" className="form-control"
                                   id="username" name="username" placeholder="Enter your username"
                                   value={user.username} onChange={handleChange}/>
                        </div>

                        <div className="form-group mb-2">
                            <label htmlFor="password">Password</label>
                            <input type="password" className="form-control"
                                   id="password" name="password" placeholder="Enter your password"
                                   value={user.password} onChange={handleChange}/>
                        </div>

                        <div className="form-group mb-2">
                            <label htmlFor="fullname">Fullname</label>
                            <input type="text" className="form-control"
                                   id="fullname" name="fullname" placeholder="Enter your fullname"
                                   value={user.fullname} onChange={handleChange}/>
                        </div>

                        <div className="form-group mb-2">
                            <label htmlFor="email">Name</label>
                            <input type="text" className="form-control"
                                   id="email" name="email" placeholder="Enter your email"
                                   value={user.email} onChange={handleChange}/>
                        </div>

                        <div className="form-group mb-2">
                            <label htmlFor="gender">Gender</label>
                            <select id="gender" name="gender" value={user.gender}
                                    onChange={handleChange}>
                                <option value="">Select gender</option>
                                <option value="0">Nam</option>
                                <option value="1">Nữ</option>
                            </select>
                        </div>

                        <div className="form-group mb-2">
                            <label>Role</label>
                            {/* <select id="role" name="role" value={user.role ? user.role.roleId: ''}
                                    onChange={e => handleChangeRole(e)}>
                                <option value="">Select role</option>
                                {allRoles.map(role => (
                                    <option key={role.roleId} value={role.roleId}>{role.roleName}</option>
                                ))}
                            </select> */}

                            {allRoles.map(role =>(
                                <div class="form-check">
                                    <input key={'role-${role.roleId}'}  className="form-check-input" 
                                    type="checkbox" value={role.roleId} 
                                    onChange={(e) => {
                                        setListSelectedRoles([
                                            ...listSelectedRoles, 
                                            e.target.value
                                        ])
                                    }}/>
                                  {role.roleName}
                                </div>
                            ))}

                        </div>
                    </form>
                </div>

            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
                <Button variant="primary" onClick={() => handleSubmitForm()}>
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ModalSubmitForm;