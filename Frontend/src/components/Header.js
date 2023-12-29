import {Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import logoApp from '../assets/images/logo192.png'
import { useLocation, NavLink, useNavigate} from "react-router-dom";
import { toast } from "react-toastify";
import { logoutAPI } from "../services/AuthService";
import { useEffect } from "react";

function Header(props) {
    const navigate = useNavigate();

    const handleLogout = async() =>{
        localStorage.removeItem("token");
        navigate("/login")
        
    }

    useEffect(() =>{
        if (!localStorage.getItem("token")){
            navigate("/login")
        }
    }, [])

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="/">
                    <img
                        src={logoApp}
                        width="30"
                        height="30"
                        className="d-inline-block align-top me-2"
                        alt="logo App"
                    />
                    <span>Sparta Cris</span>
                    </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto" >
                        <NavLink to="/home" className="nav-link" >Home</NavLink>
                        <NavLink to="/users" className="nav-link">Manage Users</NavLink>
                    </Nav>

                    <Nav>
                        <NavDropdown title="Setting" >
                            <NavLink to="/login" className="dropdown-item" >Login</NavLink>
                            <NavDropdown.Item onClick={() => handleLogout()}>Logout</NavDropdown.Item>
                                {/*<NavDropdown.Divider />*/}
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;