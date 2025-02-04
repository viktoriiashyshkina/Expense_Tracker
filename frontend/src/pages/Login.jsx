import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; 
import axios from "axios";
import "./Login.css"; 

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate(); 

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      // Send login request to backend
      const response = await axios.post("http://localhost:8080/api/login", {
        username,
        password,
      });
      const token = response.data.token; 
      localStorage.setItem('jwtToken', token); 
  
  
      navigate("/dashboard");
    } catch (error) {
      setMessage("Login failed! Please check your credentials.");
      console.error("Login error:", error);
    }
  };

  return (
    <div className="form-container">
      <h1 className="form-header">Login</h1>
      <form onSubmit={handleLogin} className="login-form">
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="form-input"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="form-input"
        />
        <button type="submit" className="form-button">
          Login
        </button>
      </form>
      {message && <p className="form-message">{message}</p>}
    </div>
  );
}

export default Login;

