import './App.scss';
import {Container} from "react-bootstrap";
import TableUsers from "./components/TableUsers";
import Header from "./components/Header";

function App() {
  return (
      <div className="app-container">
        <Header />
        <Container>
          <TableUsers />
        </Container>

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
      </div>
  );
}

export default App;
