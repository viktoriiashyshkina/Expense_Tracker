import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import './TransactionPage.css';

function TransactionPage() {
  const [transactions, setTransactions] = useState([]);
  const [newTransaction, setNewTransaction] = useState({
    title: '',
    amount: '',
    type: 'INCOME',
    date: '',
    category: '',
  });
  const [editingTransaction, setEditingTransaction] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      navigate('/login');
    } else {
      fetchTransactions();
    }
  }, [navigate]);

  const fetchTransactions = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/api/transactions', {
        headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` },
      });
      setTransactions(response.data);
    } catch (error) {
      console.error('Error fetching transactions:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddTransaction = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        'http://localhost:8080/api/transactions/addTransaction',
        { ...newTransaction, category: { name: newTransaction.category } },
        {
          headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` },
        }
      );
      setTransactions([...transactions, response.data]);
      setNewTransaction({ title: '', amount: '', type: 'INCOME', date: '', category: '' });
    } catch (error) {
      console.error('Error adding transaction:', error);
    }
  };

  const handleUpdateTransaction = async (e) => {
    e.preventDefault();
    try {
      
        const response = await axios.post(
            'http://localhost:8080/api/transactions/updateTransaction',
            { ...editingTransaction, category: { name: editingTransaction.category } },
            {
                headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` },
            }
        );

       
        setTransactions(
            transactions.map((transaction) =>
                transaction.id === response.data.id ? response.data : transaction
            )
        );
        setEditingTransaction(null); 
    } catch (error) {
        console.error('Error updating transaction:', error);
    }
};


  const handleDeleteTransaction = async (id) => {
    if (window.confirm('Are you sure you want to delete this transaction?')) {
      try {
        await axios.delete(`http://localhost:8080/api/transactions/delete/${id}`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('jwtToken')}` },
        });
        setTransactions(transactions.filter((transaction) => transaction.id !== id));
      } catch (error) {
        console.error('Error deleting transaction:', error);
      }
    }
  };

  return (
    <div className="transaction-page">
      <h1>Manage Transactions</h1>
      <div className="transaction-form">
        <h2>{editingTransaction ? 'Update Transaction' : 'Add New Transaction'}</h2>
        <form onSubmit={editingTransaction ? handleUpdateTransaction : handleAddTransaction}>
          <div>
            <label>Title</label>
            <input
              type="text"
              value={editingTransaction ? editingTransaction.title : newTransaction.title}
              onChange={(e) =>
                editingTransaction
                  ? setEditingTransaction({ ...editingTransaction, title: e.target.value })
                  : setNewTransaction({ ...newTransaction, title: e.target.value })
              }
              required
            />
          </div>
          <div>
            <label>Amount</label>
            <input
              type="number"
              value={editingTransaction ? editingTransaction.amount : newTransaction.amount}
              onChange={(e) =>
                editingTransaction
                  ? setEditingTransaction({ ...editingTransaction, amount: e.target.value })
                  : setNewTransaction({ ...newTransaction, amount: e.target.value })
              }
              required
            />
          </div>
          <div>
            <label>Type</label>
            <select
              value={editingTransaction ? editingTransaction.type : newTransaction.type}
              onChange={(e) =>
                editingTransaction
                  ? setEditingTransaction({ ...editingTransaction, type: e.target.value })
                  : setNewTransaction({ ...newTransaction, type: e.target.value })
              }
              required
            >
              <option value="INCOME">INCOME</option>
              <option value="EXPENSE">EXPENSE</option>
            </select>
          </div>
          <div>
            <label>Date</label>
            <input
              type="datetime-local"
              value={editingTransaction ? editingTransaction.date : newTransaction.date}
              onChange={(e) =>
                editingTransaction
                  ? setEditingTransaction({ ...editingTransaction, date: e.target.value })
                  : setNewTransaction({ ...newTransaction, date: e.target.value })
              }
              required
            />
          </div>
          <div>
            <label>Category</label>
            <input
              type="text"
              value={editingTransaction ? editingTransaction.category : newTransaction.category}
              onChange={(e) =>
                editingTransaction
                  ? setEditingTransaction({ ...editingTransaction, category: e.target.value })
                  : setNewTransaction({ ...newTransaction, category: e.target.value })
              }
              required
            />
          </div>
          <button type="submit">{editingTransaction ? 'Update Transaction' : 'Add Transaction'}</button>
        </form>
      </div>

      <div className="transaction-list">
        <h2>All Transactions</h2>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <ul>
            {transactions.length > 0 ? (
              transactions.map((transaction) => (
                <li key={transaction.id} className="transaction-item">
                  <div>
                    <strong>{transaction.title}</strong> - ${transaction.amount} ({transaction.type})
                    <br />
                    <span>{new Date(transaction.date).toLocaleString()}</span>
                    <br />
                    <span>Category: {transaction.category.name}</span>
                    <br />
                    <button onClick={() => setEditingTransaction(transaction)}>Edit</button>
                    <button onClick={() => handleDeleteTransaction(transaction.id)}>Delete</button>
                  </div>
                </li>
              ))
            ) : (
              <p>No transactions found.</p>
            )}
          </ul>
        )}
      </div>
    </div>
  );
}

export default TransactionPage;
