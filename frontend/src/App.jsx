import { useState } from 'react';
import { BrowserRouter, Routes, Route, NavLink } from 'react-router-dom';
import { LayoutDashboard, Utensils, Users, ClipboardList, Store, ChevronLeft, ChevronRight } from 'lucide-react';
import './App.css'; // Es crucial importar el archivo de estilos

import Dashboard from './pages/Dashboard';
import GestionAlimentos from './pages/GestionAlimentos';
import GenerarPedido from './pages/GenerarPedido';
import ListaPersonal from './pages/ListaPersonal';

function Sidebar({ collapsed, onToggle }) {
    // Función para determinar si el enlace está activo y aplicarle la clase correspondiente
    const linkClass = ({ isActive }) => `nav-link ${isActive ? 'active' : ''}`;

    return (
        <aside className={`sidebar ${collapsed ? 'collapsed' : ''}`}>
            <div className="sidebar-header">
                <Store size={28} className="logo-icon" />

                {!collapsed && (
                    <div className="logo-text">
                        <h2>Restaurante</h2>
                        <span>Gestión MVC</span>
                    </div>
                )}

                {/* Botón flotante para colapsar/expandir el menú */}
                <button className="toggle-btn" onClick={onToggle}>
                    {collapsed ? <ChevronRight size={18} /> : <ChevronLeft size={18} />}
                </button>
            </div>

            <nav className="sidebar-nav">
                <NavLink to="/" end className={linkClass} title="Panel Principal">
                    <LayoutDashboard size={22} />
                    {!collapsed && <span>Panel Principal</span>}
                </NavLink>

                <NavLink to="/alimentos" className={linkClass} title="Alimentos">
                    <Utensils size={22} />
                    {!collapsed && <span>Alimentos</span>}
                </NavLink>

                <NavLink to="/pedidos" className={linkClass} title="Comandas">
                    <ClipboardList size={22} />
                    {!collapsed && <span>Comandas / Pedidos</span>}
                </NavLink>

                <NavLink to="/personal" className={linkClass} title="Personal">
                    <Users size={22} />
                    {!collapsed && <span>Personal Activo</span>}
                </NavLink>
            </nav>
        </aside>
    );
}

export default function App() {
    const [collapsed, setCollapsed] = useState(false);

    return (
        <BrowserRouter>
            <div className="app-layout">
                <Sidebar collapsed={collapsed} onToggle={() => setCollapsed(!collapsed)} />
                <main className="main-content">
                    <Routes>
                        <Route path="/" element={<Dashboard />} />
                        <Route path="/alimentos" element={<GestionAlimentos />} />
                        <Route path="/pedidos" element={<GenerarPedido />} />
                        <Route path="/personal" element={<ListaPersonal />} />
                    </Routes>
                </main>
            </div>
        </BrowserRouter>
    );
}