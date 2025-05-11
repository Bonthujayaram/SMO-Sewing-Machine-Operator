import { Link, useLocation } from 'react-router-dom';
import { BarChart3, Package, Users, Activity, Menu, X } from 'lucide-react';
import { useState } from 'react';

const navItems = [
  { path: '/', icon: Users, label: 'Worker Dashboard' },
  { path: '/machine', icon: BarChart3, label: 'Machine Dashboard' },
  { path: '/bundle', icon: Package, label: 'Bundle Dashboard' },
  { path: '/overall', icon: Activity, label: 'Overall Dashboard' },
];

export function Sidebar() {
  const location = useLocation();
  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      {/* Mobile Menu Button */}
      <button 
        onClick={() => setIsOpen(!isOpen)}
        className="md:hidden fixed top-4 left-4 z-50 p-2 rounded-lg bg-gray-900 text-white"
      >
        {isOpen ? <X size={24} /> : <Menu size={24} />}
      </button>

      {/* Overlay for mobile */}
      {isOpen && (
        <div 
          className="md:hidden fixed inset-0 bg-black bg-opacity-50 z-30"
          onClick={() => setIsOpen(false)}
        />
      )}

      {/* Sidebar */}
      <div className={`
        fixed z-40 h-screen bg-gray-900 text-white p-4
        transition-transform duration-300 ease-in-out
        md:translate-x-0 md:w-64 md:z-10
        w-64 top-0
        ${isOpen ? 'translate-x-0' : '-translate-x-full'}
      `}>
        <div className="mb-8 mt-12 md:mt-0">
          <h1 className="text-xl md:text-2xl font-bold">Analytics Dashboard</h1>
        </div>
        <nav>
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = location.pathname === item.path;
            return (
              <Link
                key={item.path}
                to={item.path}
                onClick={() => setIsOpen(false)}
                className={`flex items-center gap-3 p-3 rounded-lg mb-2 transition-colors ${
                  isActive
                    ? 'bg-blue-600 text-white'
                    : 'text-gray-300 hover:bg-gray-800'
                }`}
              >
                <Icon size={20} />
                <span>{item.label}</span>
              </Link>
            );
          })}
        </nav>
      </div>
    </>
  );
}