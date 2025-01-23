import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import "./HomePage.css";

// Register Chart.js components
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const HomePage = () => {
  const [chartData, setChartData] = useState({
    labels: ["Income", "Expenses", "Net Balance"],
    datasets: [
      {
        label: "Financial Data",
        data: [5000, 2000, 3000], // Initial demo data
        backgroundColor: ["#4caf50", "#f44336", "#2196f3"],
      },
    ],
  });

  const updateChartData = () => {
    const randomIncome = Math.floor(Math.random() * 10000 + 5000);
    const randomExpenses = Math.floor(Math.random() * 5000 + 1000);
    const netBalance = randomIncome - randomExpenses;

    setChartData({
      labels: ["Income", "Expenses", "Net Balance"],
      datasets: [
        {
          label: "Financial Data",
          data: [randomIncome, randomExpenses, netBalance],
          backgroundColor: ["#4caf50", "#f44336", "#2196f3"],
        },
      ],
    });
  };

  useEffect(() => {
    const interval = setInterval(updateChartData, 3000); // Update every 3 seconds
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="home-page">
      <header className="home-header">
        <h1 className="main-title">Welcome to Our Expense Tracker</h1>
        <p className="tagline">Track your income and expenses with ease.</p>
      </header>

      <main className="content">
        <section className="chart-section">
          <h2 className="section-title">Your Financial Overview</h2>
          <div className="chart-container">
            <Bar
              data={chartData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
              }}
            />
          </div>
        </section>
        <section className="auth-section">
          <button
            className="login-button"
            onClick={() => alert("Redirecting to login...")}
          >
            Login
          </button>
          <button
            className="signup-button"
            onClick={() => alert("Redirecting to signup...")}
          >
            Sign Up
          </button>
        </section>
      </main>
    </div>
  );
};

export default HomePage;