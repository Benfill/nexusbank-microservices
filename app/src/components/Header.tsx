import React, { useState } from "react";

const Header = () => {
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  return (
    <header className="bg-white shadow-lg py-4 sticky top-0 z-50">
      <div className="container mx-auto flex items-center justify-between px-4">
        {/* Logo */}
        <a
          href="/"
          className="flex items-center text-primary hover:text-secondary"
        >
          <span className="text-2xl font-bold">NexusBank</span>
        </a>

        {/* Mobile Menu Button */}
        <div className="md:hidden">
          <button
            onClick={toggleMobileMenu}
            className="text-gray-800 hover:text-primary focus:outline-none transition-colors duration-300"
          >
            <svg
              className="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M4 6h16M4 12h16m-7 6h7"
              />
            </svg>
          </button>
        </div>

        {/* Desktop Navigation */}
        <nav className="hidden md:block">
          <ul className="flex space-x-8">
            <li>
              <a
                href="/"
                className="hover:text-primary transition-colors duration-300"
              >
                Dashboard
              </a>
            </li>
            <li>
              <a
                href="clients"
                className="hover:text-primary transition-colors duration-300"
              >
                Customers
              </a>
            </li>
            <li>
              <a
                href="accounts"
                className="hover:text-primary transition-colors duration-300"
              >
                Accounts
              </a>
            </li>
          </ul>
        </nav>
      </div>

      {/* Mobile Menu */}
      <nav
        className={`md:hidden bg-gray-50 border-t border-gray-200 transition-all duration-300 ease-in-out ${
          isMobileMenuOpen ? "block" : "hidden"
        }`}
      >
        <ul className="px-4 py-2">
          <li>
            <a href="/" className="block py-2 hover:text-primary">
              Dashboard
            </a>
          </li>
          <li>
            <a href="clients" className="block py-2 hover:text-primary">
              Customers
            </a>
          </li>
          <li>
            <a href="/accounts" className="block py-2 hover:text-primary">
              Accounts
            </a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
