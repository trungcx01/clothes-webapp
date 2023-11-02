import {Container, Nav, Navbar, NavDropdown} from "react-bootstrap";

function Header() {
    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="/">Sparta Cris</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto" activeKey={'/users'} >
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/users">Manage Users</Nav.Link>
                        <NavDropdown title="Setting" id="basic-nav-dropdown">
                            <NavDropdown.Item href="/login">Login</NavDropdown.Item>
                            <NavDropdown.Item href="/logout">
                                Logout
                            </NavDropdown.Item>
                            {/*<NavDropdown.Divider />*/}
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;