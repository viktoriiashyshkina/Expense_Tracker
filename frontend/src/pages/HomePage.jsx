import React from 'react';
import './HomePage.css'; // Import the updated CSS file for styling

const HomePage = ({ isAuthenticated, message }) => {
  return (
    <div className="home-page">
      <header className="home-header">
        <h1 className="main-title">Welcome to Our Expense Tracker</h1>
        <p className="tagline">Track your income and expenses with ease.</p>
      </header>

      <main className="content">
        <section className="intro">
          {isAuthenticated ? (
            <>
              <h2 className="section-title">Your Dashboard</h2>
              <div className="dashboard-info">
                <p className="section-description">Here you can see your financial summary:</p>
                <div className="financial-summary">
                  <div className="summary-item">
                    <h3 className="summary-title">Total Income:</h3>
                    <p className="summary-value">$5,000</p>
                  </div>
                  <div className="summary-item">
                    <h3 className="summary-title">Total Expenses:</h3>
                    <p className="summary-value">$2,000</p>
                  </div>
                  <div className="summary-item">
                    <h3 className="summary-title">Net Balance:</h3>
                    <p className="summary-value">$3,000</p>
                  </div>
                </div>
                <button
                  className="action-button"
                  onClick={() => alert('Redirecting to your transactions...')}
                >
                  View Transactions
                </button>
              </div>
            </>
          ) : (
            <>
              <h2 className="section-title">Please Log In</h2>
              <p className="login-message">{message || 'Access your financial dashboard by logging in.'}</p>
            </>
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


