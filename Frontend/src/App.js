import './App.scss';
import {Container} from "react-bootstrap";
import TableUsers from "./components/TableUsers";
import Header from "./components/Header";
import Home from './components/Home';
import { Routes, Route} from 'react-router-dom';
import Login from './components/Login';

function App() {
  return (
    <>
      <div className="app-container">
        <Header />
        <Container>
          <Routes>
            <Route path='/' element={<Home/>} />
            <Route path="/users" element={<TableUsers />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </Container>

        
      </div>
      {/*<ToastContainer*/}
        {/*    position="top-right"*/}
        {/*    autoClose={2000}*/}
        {/*    hideProgressBar={false}*/}
        {/*    newestOnTop={false}*/}
        {/*    closeOnClick*/}
        {/*    rtl={false}*/}
        {/*    pauseOnFocusLoss*/}
        {/*    draggable*/}
        {/*    pauseOnHover*/}
        {/*    theme="light"*/}
        {/*/>*/}
      </>
  );
}

export default App;
