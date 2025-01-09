
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './HomePage.css';  // Import CSS file for styling

const HomePage = () => {
  const [message, setMessage] = useState('Loading...');
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    // Retrieve the token from localStorage
    const token = localStorage.getItem('jwtToken');

    // Set up the headers conditionally if the token exists
    const headers = token
      ? { Authorization: `Bearer ${token}` }
      : {};

    // Fetch data from the backend
    axios.get('http://localhost:8080/api/home', { headers })
      .then(response => {
        setMessage(response.data.message || 'Welcome to the Home Page!');
        setIsAuthenticated(true);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
        setMessage('Unable to load content. Please try again.');
        setIsAuthenticated(false);
      });
  }, []);

  return (
    <div className="home-page">
      <header className="home-header">
        <h1>Welcome to Our Expense Tracker</h1>
        <p className="tagline">Track your income and expenses with ease.</p>
      </header>

      <main className="content">
        <section className="intro">
          <h2>{isAuthenticated ? 'Your Dashboard' : 'Please Log In'}</h2>
          {isAuthenticated ? (
            <div className="dashboard-info">
              <p>Here you can see your financial summary:</p>
              <div className="financial-summary">
                <div className="summary-item">
                  <h3>Total Income:</h3>
                  <p>$5,000</p>
                </div>
                <div className="summary-item">
                  <h3>Total Expenses:</h3>
                  <p>$2,000</p>
                </div>
                <div className="summary-item">
                  <h3>Net Balance:</h3>
                  <p>$3,000</p>
                </div>
              </div>
              <button onClick={() => alert('Redirecting to your transactions...')}>
                View Transactions
              </button>
            </div>
          ) : (
            <p>{message}</p>
          )}
        </section>

      </main>

      <footer className="home-footer">
        <p>&copy; 2025 Expense Tracker. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default HomePage;

