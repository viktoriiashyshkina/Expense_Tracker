// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
// import './App.css'
//
// function App() {
//   const [count, setCount] = useState(0)
//
//   return (
//     <>
//       <div>
//         <a href="https://vite.dev" target="_blank">
//           <img src={viteLogo} className="logo" alt="Vite logo" />
//         </a>
//         <a href="https://react.dev" target="_blank">
//           <img src={reactLogo} className="logo react" alt="React logo" />
//         </a>
//       </div>
//       <h1>Vite + React</h1>
//       <div className="card">
//         <button onClick={() => setCount((count) => count + 1)}>
//           count is {count}
//         </button>
//         <p>
//           Edit <code>src/App.jsx</code> and save to test HMR
//         </p>
//       </div>
//       <p className="read-the-docs">
//         Click on the Vite and React logos to learn more
//       </p>
//     </>
//   )
// }
//
// export default App


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