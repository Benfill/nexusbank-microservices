import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import ClientPage from "./pages/CustomerPage";
import AccountPage from "./pages/AccountPage";
import Header from "./components/Header";
import Footer from "./components/Footer";

const App: React.FC = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Header />
      <main className="flex-grow">
        <Router>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/clients" element={<ClientPage />} />
            <Route path="/accounts" element={<AccountPage />} />
          </Routes>
        </Router>
      </main>

      <Footer />
    </div>
  );
};

export default App;
