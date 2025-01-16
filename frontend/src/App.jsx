import React, { useState, useEffect } from "react";
import axios from "axios";
import HomePage from "./pages/HomePage";

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
    <div className="App">
      <HomePage />
    </div>
  );
}

export default App;
