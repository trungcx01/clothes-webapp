import {useState, useEffect} from "react";
import { loginAPI } from "../services/AuthService";
import { toast } from "react-toastify";
import { NavLink, useNavigate} from "react-router-dom";



const Login = () =>{
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false)
    const [loadingData, setLoadingData] = useState(false)  
    
   useEffect(() => {
        let token = localStorage.getItem("token");
        if (token){
            navigate("/home")
        }
   },[]);


   const handleLogin = async () => {
    if(!username || !password) {
      toast.error("Email/Password is required!");
      return;
    }
  
    setLoadingData(true);
  
    try {
      const res = await loginAPI(username, password);
      const token = res.data.accessToken;  
      localStorage.setItem("token", token);
      navigate("/");
    } catch (error) {
      toast.error("Login failed!");
    }
      setLoadingData(false);
  };

   
    return (
        <>
            <div className="login-container col-12 col-sm-4">
                <div className="title">Log in</div>
                <div className="text">Email or username</div>

                <input 
                type="text" 
                placeholder="Email or username"
                value={username}
                name="username"
                onChange={(e)=> setUsername(e.target.value)}
                />

                <div className="input-2">
                    <input type={showPassword ? "text" : "password"}
                    placeholder="Password"
                    value={password}
                    name="password"
                    onChange={(e) => setPassword(e.target.value)}
                    />

                <i className={showPassword ? "fa-regular fa-eye" : "fa-regular fa-eye-slash"} 
                    onClick={() => setShowPassword(!showPassword)}></i>
                </div>

                <button 
                className={username && password ? "active" : ""}
                disabled={(username && password) ? false : true} 
                onClick={handleLogin}>
                    {loadingData && <i className="fa-solid fa-sync fa-spin"></i>} 
                    &nbsp;Login
                </button>
                <div className="back">
                    <NavLink to="/users" className="nav-link" >
                        <i className="fa-solid fa-chevron-left"></i> Go Back
                        </NavLink>
                </div>
            </div>
        </>
    )
}
export default Login;