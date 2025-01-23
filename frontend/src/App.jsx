import React, { useState, useEffect } from "react";
import axios from "axios";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login"; // Assuming you have these components
import Register from "./pages/Register"; // Assuming you have these components
import Dashboard from "./pages/Dashboard"; 

function App() {
  const [message, setMessage] = useState('Loading...');

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    const headers = token
      ? { Authorization: `Bearer ${token}` }
      : {};

    // Make the API request
    axios.get('http://localhost:8080/api/home', { headers })
      .then(response => {
        // If the response has a message, display it
        setMessage(response.data.message || 'Welcome to the Home Page!');
      })
      .catch(error => {
        console.error('Error fetching data:', error);
        
        // Check if the error has a response and display the response's message if available
        if (error.response) {
          setMessage(`Error: ${error.response.data.message || 'Unable to load content. Please try again.'}`);
        } else {
          setMessage('Unable to load content. Please try again.');
        }
      });
  }, []);

  return (
<Router>
      <div className="App">
        <header className="navbar">
          <nav>
            <ul className="nav-links">
              <li>
                <Link to="/">HomePage</Link>
              </li>
              <li>
                <Link to="/login">Login</Link>
              </li>
              <li>
                <Link to="/signup">Register</Link>
              </li>
              <li>
                <Link to="/dashboard">Dashboard</Link>
              </li>
            </ul>
          </nav>
        </header>
        <main className="content">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Register />} />
            <Route path="/dashboard" element={<Dashboard />} />
          </Routes>
        </main>
      </div>
    </Router>

    
  );
}

export default App;
