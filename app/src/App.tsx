import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router";
import HomePage from "./pages/HomePage";
import ClientPage from "./pages/CustomerPage";
import AccountPage from "./pages/AccountPage";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/clients" element={<ClientPage />} />
        <Route path="/accounts" element={<AccountPage />} />
      </Routes>
    </Router>
  );
};

export default App;
