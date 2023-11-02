import {Button, Modal} from "react-bootstrap";
import {fetchDeleteUser} from "../services/UserService";
import {useEffect} from "react";

const ModalDeleteUser = (props) =>{
    const {show, handleClose, deleteUserId, getAllUsers} = props;

    const handleDeleteUser = async() => {
        try {
            let res = await fetchDeleteUser(deleteUserId);
            handleClose();
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    }

    useEffect(() =>{
        if (!show){
            getAllUsers("", "");
        }
    }, [show]);

    return(
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Delete User</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Bạn có chắc chắn muốn xóa user này không?</p>
                </Modal.Body>

                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                       No
                    </Button>
                    <Button variant="primary" onClick={() => handleDeleteUser()}>
                        Yes
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}
export default ModalDeleteUser;