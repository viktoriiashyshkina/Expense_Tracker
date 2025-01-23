import React from "react";
import { Route, Navigate } from "react-router-dom";

// PrivateRoute component to protect routes for authenticated users
const PrivateRoute = ({ element, ...rest }) => {
  const token = localStorage.getItem("jwtToken"); // Get JWT token from localStorage

  // If no token is present, redirect to login page
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  return element; // If token exists, render the route component
};

export default PrivateRoute;
