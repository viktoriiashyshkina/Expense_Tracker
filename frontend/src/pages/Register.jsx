
import React, { useState } from "react";
import api from "./axiosConfig";
import "./Register.css";
import { useNavigate } from "react-router-dom";

function Register() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post("/signup", { username, email, password });
      setMessage(response.data); // Success message from the backend
      navigate("/dashboard")

    } catch (error) {
      setMessage("Sign-up failed! Please try again.");
    }
  };

  return (
    <div className="form-container">
    <h1 className="form-header">Sign Up</h1>
    <form onSubmit={handleSignup} className="signup-form">
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="form-input"
      />
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
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
        Sign Up
      </button>
    </form>
    {message && <p className="form-message">{message}</p>}
  </div>
  );
}


export default Register