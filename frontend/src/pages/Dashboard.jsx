import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; // Import Link and useNavigate from react-router-dom
import axios from 'axios';
import { Line } from 'react-chartjs-2'; 
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js'; 
import './Dashboard.css';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

function Dashboard() {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpenses, setTotalExpenses] = useState(0);
  const [totalBalance, setTotalBalance] = useState(0);
  const [username, setUsername] = useState('');
  const [error, setError] = useState(null); 
  const navigate = useNavigate();

  const fetchDashboardData = async () => {
    setLoading(true);
    setError(null); // Reset error state before making the request
    
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      navigate('/login');
      setLoading(false); // Stop loading if no token
      return;
    }

    try {
      const headers = { Authorization: `Bearer ${token}` };
  
      const response = await axios.get('http://localhost:8080/dashboard', { headers });
      setUsername(response.data.username);

      const transactionsResponse = await axios.get('http://localhost:8080/api/transactions', { headers });
      setTransactions(transactionsResponse.data);
      calculateBalance(transactionsResponse.data);
  
    } catch (error) {
      console.error('Error during API request:', error);
      
      if (error.response) {
        setError(`Error: ${error.response.status} - ${error.response.data.message || error.response.statusText}`);
      } else if (error.request) {
        setError('Error: No response received from the server.');
      } else {
        setError(`Error: ${error.message}`);
      }
    } finally {
      setLoading(false);
    }
  };

  const calculateBalance = (transactions) => {
    const income = transactions.filter(transaction => transaction.type === 'INCOME');
    const expense = transactions.filter(transaction => transaction.type === 'EXPENSE');

    const totalIncomeValue = income.reduce((acc, transaction) => acc + transaction.amount, 0);
    const totalExpensesValue = expense.reduce((acc, transaction) => acc + transaction.amount, 0);
    const balance = totalIncomeValue - totalExpensesValue;

    setTotalIncome(totalIncomeValue);
    setTotalExpenses(totalExpensesValue);
    setTotalBalance(balance);
  };

  const getCumulativeBalance = (transactions) => {
    const sortedTransactions = transactions.sort((a, b) => new Date(a.date) - new Date(b.date));
    let cumulativeBalance = 0;
    const balanceData = [];
    const labels = [];

    sortedTransactions.forEach(transaction => {
      const amount = transaction.type === 'INCOME' ? transaction.amount : -transaction.amount;
      cumulativeBalance += amount;
      labels.push(new Date(transaction.date).toLocaleDateString());
      balanceData.push(cumulativeBalance);
    });

    return { labels, balanceData };
  };

  const { labels, balanceData } = getCumulativeBalance(transactions);

  const chartData = {
    labels: labels,
    datasets: [
      {
        label: 'Total Balance',
        data: balanceData,
        borderColor: 'rgba(75, 192, 192, 1)',
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        fill: true,
      },
    ],
  };

  useEffect(() => {
    fetchDashboardData();
  }, []);

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="dashboard">
      <div className="user-cabinet">
        <div className="header">
          <h1>Welcome, {username || 'User'}</h1>
          <p>Manage your finances easily and effectively.</p>
        </div>

        <div className="dashboard-stats">
          <div className="stat-box">
            <h2>Total Income</h2>
            <p>${totalIncome}</p>
          </div>
          <div className="stat-box">
            <h2>Total Expenses</h2>
            <p>${totalExpenses}</p>
          </div>
          <div className="stat-box">
            <h2>Total Balance</h2>
            <p>${totalBalance}</p>
          </div>
        </div>

        <div className="chart-container">
          <h2>Total Balance Over Time</h2>
          <Line data={chartData} />
        </div>

        <div className="actions">
          <Link to="/transactions" className="btn-link">
            Go to Transactions
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
